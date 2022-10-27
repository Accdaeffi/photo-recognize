package ru.itmo.iad.photorecognize.domain.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import ru.itmo.iad.photorecognize.domain.dao.AsessorDao;

public interface AsessorRepository extends MongoRepository<AsessorDao, ObjectId> {

	Optional<AsessorDao> findByTelegramId(String telegramId);
	
}
