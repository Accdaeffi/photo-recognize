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

@Slf4j
@Service
public class PhotoGetter {

    @Autowired
    Bot bot;

    @Nullable
    public File getUserImage(PhotoSize photoSize) {
        String filePath;

        if (photoSize == null) {
            log.error("Error getting max photoSize size!");
            return null;
        }

        if (photoSize.getFilePath() != null) {
            filePath = photoSize.getFilePath();
        } else {
            GetFile getFileMethod = new GetFile();
            getFileMethod.setFileId(photoSize.getFileId());

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
            log.error("Error downloading photoSize!", e);
            return null;
        }
    }
}
