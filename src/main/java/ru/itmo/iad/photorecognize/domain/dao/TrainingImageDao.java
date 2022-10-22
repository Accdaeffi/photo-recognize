package ru.itmo.iad.photorecognize.domain.dao;

import java.sql.Timestamp;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Builder;
import lombok.Data;
import ru.itmo.iad.photorecognize.domain.Dataset;

@Document(collection = "training_image")
@Data
@Builder
public class TrainingImageDao {
	
	@Id
	@Field
	private String id;
	
	@Field
	private String imageId;
	
	@Field
	private String fileName;
	
	@Field
	private Dataset dataset;
	
	@Field 
	private Timestamp dt_created;
}
