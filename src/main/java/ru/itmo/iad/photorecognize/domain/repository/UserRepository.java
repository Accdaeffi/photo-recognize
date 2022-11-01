package ru.itmo.iad.photorecognize.domain.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.itmo.iad.photorecognize.domain.dao.UserDao;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserDao, ObjectId> {

	Optional<UserDao> findByTelegramId(String telegramId);
	
}
