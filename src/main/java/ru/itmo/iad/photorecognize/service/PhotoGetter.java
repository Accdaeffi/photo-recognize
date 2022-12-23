package ru.itmo.iad.photorecognize.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.itmo.iad.photorecognize.telegram.Bot;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class PhotoGetter {

    @Autowired
    Bot bot;

    @Nullable
    public File getUserImage(List<PhotoSize> photoSizes) {
        String filePath;

        PhotoSize photo = photoSizes.stream().max(Comparator.comparing(PhotoSize::getFileSize)).orElse(null);

        if (photo == null) {
            log.error("Error getting max photo size!");
            return null;
        }

        if (photo.getFilePath() != null) {
            filePath = photo.getFilePath();
        } else {
            GetFile getFileMethod = new GetFile();
            getFileMethod.setFileId(photo.getFileId());

            try {
                org.telegram.telegrambots.meta.api.objects.File file = bot.execute(getFileMethod);

                filePath = file.getFilePath();
            } catch (TelegramApiException e) {
                log.error("Error getting filepath!", e);
                return null;
            }
        }

        try {
            return bot.downloadFile(filePath);
        } catch (TelegramApiException e) {
            log.error("Error downloading photo!", e);
            return null;
        }
    }
}
