package ru.itmo.iad.photorecognize.domain.dao;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Builder;
import lombok.Data;

@Document(collection = "assessor")
@Data
@Builder
public class AssessorDao {

	@Field
	ObjectId _id;

	@Field
	String telegramId;

	@Field
	String username;

	@Field
	int honeypotCount;
}
