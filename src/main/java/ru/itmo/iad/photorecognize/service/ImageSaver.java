package ru.itmo.iad.photorecognize.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.itmo.iad.photorecognize.domain.dao.UserImageDto;
import ru.itmo.iad.photorecognize.domain.repository.UserImageRepository;
import ru.itmo.iad.photorecognize.telegram.Bot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;

@Service
public class ImageSaver {

    @Autowired
    GridFsTemplate gridFsTemplate;

    @Autowired
    UserImageRepository userImageRepository;

    @Autowired
    Bot bot;

    public ObjectId saveUserImage(String userId, PhotoSize photo) {

        String filePath;

        if (photo.getFilePath() != null) {
            filePath = photo.getFilePath();
        } else {
            GetFile getFileMethod = new GetFile();
            getFileMethod.setFileId(photo.getFileId());

            try {
                org.telegram.telegrambots.meta.api.objects.File file = bot.execute(getFileMethod);

                filePath = file.getFilePath();
            } catch (TelegramApiException e) {
                filePath = null;
                e.printStackTrace();
            }
        }

        if (filePath != null) {
            try {
                File image = bot.downloadFile(filePath);

                ObjectId fileId = gridFsTemplate.store(new FileInputStream(image), image.getName());

                var trainingImage = UserImageDto.builder()
                        ._id(ObjectId.get())
                        .fileId(fileId)
                        .senderId(userId)
                        .dtCreated(new Date())
                        .build();

                userImageRepository.save(trainingImage);

                return trainingImage.get_id();

            } catch (TelegramApiException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        return null;

    }

}
