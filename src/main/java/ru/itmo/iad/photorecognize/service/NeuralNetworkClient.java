package ru.itmo.iad.photorecognize.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.itmo.iad.photorecognize.domain.dto.ImageAssessingDto;

import java.util.Map;

@FeignClient(value = "NeuralNetworkClient", url = "${neural-network.base-url}")
public interface NeuralNetworkClient {

    @PostMapping(value = "/invocations")
    Map<String, Object> recognizePhoto(@RequestBody ImageAssessingDto imageAssessingDto);


}
