package ru.itmo.iad.photorecognize.service;

import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import ru.itmo.iad.photorecognize.domain.Label;
import ru.itmo.iad.photorecognize.telegram.response.StringResponse;

import java.io.File;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class RecognizePhotoService {
    @Autowired
    private ImageRecognizer imageRecognizer;

    @Autowired
    private MonitoringService monitoringService;

    @Timed("recognize_timer")
    public StringResponse recognize(File file) {
        Map<Label, Double> labels = sendRequest(file);

        if (labels != null) {
            log.info("Labels probabilities got!");

            String probabilities = labels.entrySet()
                    .stream()
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                    .limit(3)
                    .map(this::formatEntryText)
                    .reduce((acc, value) -> acc + "\n" + value)
                    .orElse(null);

            if (probabilities != null) {
                log.error("Photo recognize - OK");
                String result = "Самые вероятные классы:\n" + probabilities;
                monitoringService.incrementRecognizeCounter();
                return new StringResponse(result);
            } else {
                log.error("Internal error: unable to process classes.");
                monitoringService.incrementErrorRecognizeCounter();
                return new StringResponse("Ошибка при внутренней обработке полученых классов!");
            }
        } else {
            log.info("Failed to get probability list");
            monitoringService.incrementErrorRecognizeCounter();
            return new StringResponse("Ошибка при получении списка вероятностей!");
        }
    }

    @Timed("neural_network_request_timer")
    public Map<Label, Double> sendRequest(File file) {
        try {
            Map<Label, Double> labelsProbabilities = imageRecognizer.recognizePhoto(file);
            return labelsProbabilities;
        } catch (Exception e) {
            log.error("Failed to recognize photo by neural network" + e.getMessage());
            return null;
        }
    }

    private boolean checkSize(PhotoSize photo) {
        int sizeInKb = photo.getFileSize() / 1024;
        return sizeInKb >= 1 && (sizeInKb / 1024) <= 100;
    }

    private boolean checkSizes(PhotoSize photo) {
        return photo.getHeight() <= 1080 && photo.getWidth() <= 1920;
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
}
