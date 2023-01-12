package ru.itmo.iad.photorecognize.telegram.commands.photos;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import ru.itmo.iad.photorecognize.service.MonitoringService;
import ru.itmo.iad.photorecognize.service.ImageResizer;
import ru.itmo.iad.photorecognize.service.ImageSaver;
import ru.itmo.iad.photorecognize.service.PhotoGetter;
import ru.itmo.iad.photorecognize.service.RecognizePhotoService;
import ru.itmo.iad.photorecognize.telegram.commands.AbsCommand;
import ru.itmo.iad.photorecognize.telegram.response.Response;
import ru.itmo.iad.photorecognize.telegram.response.StringResponse;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.*;

@Service
@Scope("prototype")
@Slf4j
public class RecognizePhotoCommand extends AbsCommand {

    @Autowired
    PhotoGetter photoGetter;

    @Autowired
    MonitoringService monitoringService;

    @Autowired
    RecognizePhotoService recognizePhotoService;

    @Autowired
    ImageResizer resizer;

    @Autowired
    ImageSaver imageSaver;


    private final List<PhotoSize> photoSizes;

    private final String userId;

    public RecognizePhotoCommand(String userId, List<PhotoSize> photoSizes) {
        this.userId = userId;
        this.photoSizes = photoSizes;
    }

    @Override
    public Response<?> execute() {

        log.info("Received photo to recognise!");

        PhotoSize photoSize = photoSizes.stream().max(Comparator.comparing(PhotoSize::getFileSize)).orElse(null);

     /*   String checkResult = checkUserImage(photoSize);
        if (!checkResult.equals("ok")) {
            return new StringResponse(checkResult);
        }*/

        File file = null;
        try {
            file = photoGetter.getUserImage(photoSize);
        } catch (Exception e) {
            monitoringService.incrementErrorRecognizeCounter();
            log.error("Unable to get photo: " + e.getMessage());
        }

        if (file != null) {
            log.info("Photo got!");
            try {
                BufferedImage originalImage = ImageIO.read(new FileInputStream(file));
                BufferedImage resizedImage = resizer.resizeImage(originalImage, 224, 224);
                imageSaver.saveImage(userId, resizedImage, UUID.randomUUID().toString() + ".jpg");
            } catch (IOException ex) {
                log.error("Ошибка при обработке!", ex);
                return new StringResponse("Ошибка при вводе-выводе!");
            }

            return recognizePhotoService.recognize(file);
        } else {
            log.info("Getting photo failed!");
            monitoringService.incrementErrorRecognizeCounter();
            return new StringResponse("Ошибка при загрузка фото из Telegram!");
        }
    }
}
