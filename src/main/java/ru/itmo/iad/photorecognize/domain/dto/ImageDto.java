package ru.itmo.iad.photorecognize.domain.dto;

import java.io.InputStream;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageDto {

	String photoId;
	boolean isHoneypot;
	InputStream data;

}
