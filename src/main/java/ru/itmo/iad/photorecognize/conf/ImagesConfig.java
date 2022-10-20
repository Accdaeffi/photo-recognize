package ru.itmo.iad.photorecognize.conf;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.Getter;

@Service
@Getter
public class ImagesConfig {

	@Value("${images.basePath}")
	String filePath;

	int imagesCount;
	
	@PostConstruct
	public void init() {
		System.out.println(filePath);
		
		imagesCount = new File(filePath).listFiles().length;	
	}
	
}
