package ru.itmo.iad.photorecognize.service;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.File;
import java.util.Map;

@FeignClient(value = "NeuralNetworkClient", url = "${neural-network.base-url}")
public interface NeuralNetworkClient {

    @GetMapping(value = "/")
    @Headers("Content-Type: multipart/form-data")
    Map<String, Double> recognizePhoto(@RequestBody File photo);

}
