package ru.itmo.iad.photorecognize.domain.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import ru.itmo.iad.photorecognize.domain.Dataset;
import ru.itmo.iad.photorecognize.domain.dao.UserImageDto;

public interface UserImageRepository extends MongoRepository<UserImageDto, ObjectId> {
	
	List<UserImageDto> findByDataset(Dataset dataset);

}
