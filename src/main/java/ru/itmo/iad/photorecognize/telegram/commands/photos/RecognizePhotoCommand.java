package ru.itmo.iad.photorecognize.telegram.commands.photos;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

@Service
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
}
