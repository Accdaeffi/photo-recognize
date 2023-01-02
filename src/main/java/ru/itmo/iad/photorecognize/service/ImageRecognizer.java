package ru.itmo.iad.photorecognize.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.iad.photorecognize.domain.Label;
import ru.itmo.iad.photorecognize.domain.dto.ImageAssessingDto;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

@Service
public class ImageRecognizer {

    @Autowired
    NeuralNetworkClient neuralNetworkClient;

    @Nullable
    public Map<Label, Double> recognizePhoto(File file) {
        try (FileInputStream fileStream = new FileInputStream(file)) {

            byte[] photoBytes = fileStream.readAllBytes();
            ImageAssessingDto imageAssessingDto = new ImageAssessingDto(photoBytes);

            Map<String, Object> stringProbabilitiesMap = neuralNetworkClient.recognizePhoto(imageAssessingDto);

            System.out.println(stringProbabilitiesMap);

            // somehow convert
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;

    }
}
