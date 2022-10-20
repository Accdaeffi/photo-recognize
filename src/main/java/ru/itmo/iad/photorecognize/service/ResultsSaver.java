package ru.itmo.iad.photorecognize.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ru.itmo.iad.photorecognize.domain.dto.ResultDto;

@Service
@Slf4j
public class ResultsSaver {
	
	@Autowired
	ResultsCounter resultsCounter;
	
	private final static String format = "%s: photo %d is a %s";
	
	public void countResult(ResultDto result) {
		log.info(String.format(format, result.getAsessorId(), result.getPhotoId(), result.getResult().getCode()));
	
		resultsCounter.countResult(result.getPhotoId());
	}

}
