package ru.itmo.iad.photorecognize.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.itmo.iad.photorecognize.domain.Label;

@Data
@AllArgsConstructor
public class ResultDto {

	String asessorId;
	int photoId;
	Label result;
	
}
