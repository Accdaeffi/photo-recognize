package ru.itmo.iad.photorecognize.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import ru.itmo.iad.photorecognize.domain.Label;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

@Service
public class ImageRecognizer {

    @Autowired
    NeuralNetworkClient neuralNetworkClient;

    @Nullable
    public Map<Label, Double> recognizePhoto(File file) {
        try (FileInputStream fileStream = new FileInputStream(file)) {
            MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "original.jpeg", "image/jpeg", fileStream);

            Map<String, Object> stringProbabilitiesMap = neuralNetworkClient.recognizePhoto(mockMultipartFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;

    }
}
