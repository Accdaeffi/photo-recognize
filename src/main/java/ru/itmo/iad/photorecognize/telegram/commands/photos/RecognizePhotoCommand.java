package ru.itmo.iad.photorecognize.telegram.commands.photos;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import ru.itmo.iad.photorecognize.domain.Label;
import ru.itmo.iad.photorecognize.service.ImageRecognizer;
import ru.itmo.iad.photorecognize.service.PhotoGetter;
import ru.itmo.iad.photorecognize.telegram.commands.AbsCommand;
import ru.itmo.iad.photorecognize.telegram.response.Response;
import ru.itmo.iad.photorecognize.telegram.response.StringResponse;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Scope("prototype")
@Slf4j
public class RecognizePhotoCommand extends AbsCommand {

    @Autowired
    PhotoGetter photoGetter;

    @Autowired
    ImageRecognizer imageRecognizer;

    private final List<PhotoSize> photoSizes;

    public RecognizePhotoCommand(List<PhotoSize> photoSizes) {
        this.photoSizes = photoSizes;
    }

    @Override
    public Response<?> execute() {

        log.info("Received photo to recognise!");

        File file = photoGetter.getUserImage(photoSizes);

        if (file != null) {
            log.info("Photo got!");

        PhotoSize photo = photoSizes.stream().max(Comparator.comparing(PhotoSize::getFileSize)).orElse(null);

        //TODO: добавила валидацию картинок, потом нужно будет раскомментировать и поправить
        /*String result = checkUserImage(photo);
        if (!result.equals("ok")) {
            return new StringResponse(result);
        }*/

        imageSaver.saveUserImage(user.getTelegramId(), photo);
            Map<Label, Double> labelsProbabilities = imageRecognizer.recognizePhoto(file);

            log.info("Labels probabilities got!");

            String probabilities = labelsProbabilities.entrySet()
                    .stream()
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                    .limit(3)
                    .map((entry) -> String.format("%s(%f)", entry.getKey().getButtonText(), entry.getValue()))
                    .reduce((acc, value) -> acc + ", " + value)
                    .orElse(null);

            if (probabilities != null) {
                String result = "Самые вероятные классы (с вероятностями):" + probabilities;
                return new StringResponse(result);
            } else {
                return new StringResponse("Ошибка при внутренней обработки полученых классов!");
            }
        } else {
            log.info("Getting photo failed!");
            return new StringResponse("Ошибка при загрузка фото из Telegram!");
        }
    }

    private String checkUserImage(PhotoSize photo) {
        StringBuilder result = new StringBuilder();
        Optional.ofNullable(photo).ifPresentOrElse(
                (photoToCheck) -> {
                    if (!checkSizes(photoToCheck) || !checkExtension(photoToCheck)
                            || !checkSize(photoToCheck)) {
                        result.append("User image has invalid size or extension " +
                                "(max size is 1920*1080, image: png, jpg, jpeg)");
                    } else {
                        result.append("ok");
                    }
                },
                () -> result.append("Empty image")
        );
        return result.toString();
    }

    private boolean checkSize(PhotoSize photo) {
        int sizeInKb = photo.getFileSize() / 1024;
        return sizeInKb >= 1 && sizeInKb / 1024 <= 50;
    }

    private boolean checkSizes(PhotoSize photo) {
        return photo.getHeight() >= 1080 && photo.getWidth() <= 1920;
    }

    private boolean checkExtension(PhotoSize photo) {
        return photo.getFilePath().endsWith(".jpg")
                || photo.getFilePath().endsWith(".jpeg")
                || photo.getFilePath().endsWith(".png");
    }
}
