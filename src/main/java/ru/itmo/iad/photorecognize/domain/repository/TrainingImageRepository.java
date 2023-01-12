package ru.itmo.iad.photorecognize.domain.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.itmo.iad.photorecognize.domain.dao.TrainingImageDto;

public interface TrainingImageRepository extends MongoRepository<TrainingImageDto, ObjectId> {
}
