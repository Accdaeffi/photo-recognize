package ru.itmo.iad.photorecognize.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.iad.photorecognize.domain.Label;

import java.io.File;
import java.util.Map;

@Service
public class ImageRecognizer {

    @Autowired
    NeuralNetworkClient neuralNetworkClient;

    public Map<Label, Double> recognizePhoto(File file) {
        Map<String, Double> stringProbabilitiesMap = neuralNetworkClient.recognizePhoto(file);

        return null;

    }
}
