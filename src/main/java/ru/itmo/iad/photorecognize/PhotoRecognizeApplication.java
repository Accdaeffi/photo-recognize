package ru.itmo.iad.photorecognize;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PhotoRecognizeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoRecognizeApplication.class, args);
	}

}
