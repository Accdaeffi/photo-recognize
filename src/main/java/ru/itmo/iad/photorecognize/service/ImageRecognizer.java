package ru.itmo.iad.photorecognize.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.iad.photorecognize.domain.Label;
import ru.itmo.iad.photorecognize.domain.dto.ImageRecognizingDto;
import ru.itmo.iad.photorecognize.domain.dto.PredictionsDto;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ImageRecognizer {

    @Autowired
    NeuralNetworkClient neuralNetworkClient;

    @Autowired
    ImageToTensorConvertor imageConvertor;

    @Nullable
    public Map<Label, Double> recognizePhoto(File file) {
        try (FileInputStream fileStream = new FileInputStream(file)) {

            List<double[][][]> inputList = new ArrayList<>();
            inputList.add(imageConvertor.convertImage(fileStream));

            ImageRecognizingDto recognizingDto = new ImageRecognizingDto(inputList);

            PredictionsDto predictions = neuralNetworkClient.recognizePhoto(recognizingDto);

            Map<Label, Double> predictionsMap = new HashMap<>();

            for (int i = 0; i < Label.values().length; i++) {
                predictionsMap.put(Label.values()[i], predictions.getPredictions()[0][i]);
            }

            return predictionsMap;

            // somehow convert
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
