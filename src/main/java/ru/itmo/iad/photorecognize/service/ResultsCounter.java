package ru.itmo.iad.photorecognize.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.itmo.iad.photorecognize.conf.ImagesConfig;

@Service
public class ResultsCounter {

	@Autowired
	ImagesConfig imagesConfig;

	int[] counts;
	
	@PostConstruct
	public void init() {
		 counts = new int[imagesConfig.getImagesCount()];
	}
	
	public void countResult(int imageId) {
		counts[imageId] += 1;
	}
	
	public int getLeastCountImage() {
		int minimumValue = 1000;
		int minimumId = 0;
		
		for (int i = 0; i<counts.length; i++) {
			if (counts[i] < minimumValue) {
				minimumValue = counts[i];
				minimumId = i;
			}
		}
		
		return minimumId;
	}
	
	
}
