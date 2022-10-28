package ru.itmo.iad.photorecognize.domain.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import ru.itmo.iad.photorecognize.domain.dao.ImageAssessmentDao;

public interface ImageAsessmentRepository extends MongoRepository<ImageAssessmentDao, ObjectId> {

}
