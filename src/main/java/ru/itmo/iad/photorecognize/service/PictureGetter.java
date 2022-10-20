package ru.itmo.iad.photorecognize.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.itmo.iad.photorecognize.conf.ImagesConfig;
import ru.itmo.iad.photorecognize.domain.dto.PhotoDto;

@Service
public class PictureGetter {

	@Autowired
	ImagesConfig imagesConfig;
	
	@Autowired
	ResultsCounter resultsCounter;

	public PhotoDto getRandomImage() throws IOException {
		
		Random r = new Random();
		int fileNum = r.nextInt() % imagesConfig.getImagesCount();
		
		byte[] data = getImageData(fileNum);
		
		return new PhotoDto(fileNum, data);
	}
	
	public PhotoDto getLeastUserImage() throws IOException {
		int fileNum = resultsCounter.getLeastCountImage();
		
		byte[] data = getImageData(fileNum);
		
		return new PhotoDto(fileNum, data);
	}
	
	
	private byte[] getImageData(int fileNum) throws IOException {
		String fileName = String.format("%d.jpg", fileNum);
		File file = new File(imagesConfig.getFilePath()+fileName);
		
		return Files.readAllBytes(file.toPath());
	}
	
	

}
