package ru.itmo.iad.photorecognize.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.itmo.iad.photorecognize.domain.dto.ImageRecognizingDto;
import ru.itmo.iad.photorecognize.domain.dto.PredictionsDto;

@FeignClient(value = "NeuralNetworkClient", url = "${neural-network.base-url}")
public interface NeuralNetworkClient {

    @PostMapping(value = "/invocations")
    PredictionsDto recognizePhoto(@RequestBody ImageRecognizingDto imageRecognizingDto);


}
