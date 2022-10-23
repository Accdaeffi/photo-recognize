package ru.itmo.iad.photorecognize.domain.dao;

import java.sql.Timestamp;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Builder;
import lombok.Data;
import ru.itmo.iad.photorecognize.domain.Label;

@Document(collection = "image_assessment")
@Data
@Builder
public class ImageAsessmentDao {
	
	@Field
	ObjectId _id;
	
	@Field
	String by;
	
	@Field
	ObjectId image;
	
	@Field
	Label label;
	
	@Field 
	Timestamp dt_created;

}
