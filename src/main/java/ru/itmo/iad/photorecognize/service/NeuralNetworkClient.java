package ru.itmo.iad.photorecognize.service;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import ru.itmo.iad.photorecognize.conf.FeignClientConfig;

import java.util.Map;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@FeignClient(value = "NeuralNetworkClient", url = "${neural-network.base-url}", configuration = FeignClientConfig.class)
public interface NeuralNetworkClient {

    @PostMapping(value = "/", consumes = MULTIPART_FORM_DATA_VALUE)
    @Headers("Content-Type: multipart/form-data")
    Map<String, Object> recognizePhoto(@RequestPart("file") MockMultipartFile photo);


}
