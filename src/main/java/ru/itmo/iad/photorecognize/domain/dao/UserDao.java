package ru.itmo.iad.photorecognize.domain.dao;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Builder;
import lombok.Data;

@Document(collection = "user")
@Data
@Builder
public class UserDao {

	@Field
	ObjectId _id;

	@Field
	String telegramId;

	@Field
	String username;
}
