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
import java.util.*;

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

        PhotoSize photoSize = photoSizes.stream().max(Comparator.comparing(PhotoSize::getFileSize)).orElse(null);

     /*   String checkResult = checkUserImage(photoSize);
        if (!checkResult.equals("ok")) {
            return new StringResponse(checkResult);
        }*/

        File file = photoGetter.getUserImage(photoSize);

        if (file != null) {
            log.info("Photo got!");

            Map<Label, Double> labelsProbabilities = imageRecognizer.recognizePhoto(file);

            if (labelsProbabilities != null) {

                log.info("Labels probabilities got!");

                String probabilities = labelsProbabilities.entrySet()
                    .stream()
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                    .limit(3)
                    .map(this::formatEntryText)
                    .reduce((acc, value) -> acc + "\n" + value)
                    .orElse(null);

                if (probabilities != null) {
                    String result = "Самые вероятные классы:\n" + probabilities;
                    return new StringResponse(result);
                } else {
                    return new StringResponse("Ошибка при внутренней обработки полученых классов!");
                }
            } else {
                return new StringResponse("Ошибка при получении списка вероятностей!");
            }
        } else {
            log.info("Getting photo failed!");
            return new StringResponse("Ошибка при загрузка фото из Telegram!");
        }
    }

    private String formatEntryText(Map.Entry<Label, Double> entry) {
        // rounded to 1 digit after dot
        Double probability = Math.round(entry.getValue() * 1000) / 10.0;
        Label label = entry.getKey();

        return String.format(
            "▪ %.1f%% – %s (%s)",
            probability,
            label.getButtonText(),
            label.getLabelZeroLevel().getButtonText()
        );
    }

    private String checkUserImage(PhotoSize photo) {
        StringBuilder result = new StringBuilder();
        Optional.ofNullable(photo).ifPresentOrElse(
            photoToCheck -> {
                if (!checkSizes(photoToCheck) || !checkSize(photoToCheck)) {
                    result.append("Изображение неправильного размера или формата " +
                        "(максимальные размеры 1920*1080, 50Мб, форматы: png, jpg, jpeg)");
                } else {
                    result.append("ok");
                }
            },
            () -> result.append("Пустое изображение")
        );
        return result.toString();
    }

    private boolean checkSize(PhotoSize photo) {
        int sizeInKb = photo.getFileSize() / 1024;
        return sizeInKb >= 1 && (sizeInKb / 1024) <= 100;
    }

    private boolean checkSizes(PhotoSize photo) {
        return photo.getHeight() <= 1080 && photo.getWidth() <= 1920;
    }
}
