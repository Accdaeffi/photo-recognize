package ru.itmo.iad.photorecognize.service;

import java.sql.Timestamp;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.itmo.iad.photorecognize.domain.Label;
import ru.itmo.iad.photorecognize.domain.dao.ImageAsessmentDao;
import ru.itmo.iad.photorecognize.domain.repository.ImageAsessmentRepository;

@Service
public class AsessmentSaver {

	@Autowired
	ImageAsessmentRepository imageAsessmentRepository;
	
	public void saveAssessment(String userId, String photoId, Label label) {
		ImageAsessmentDao dao = ImageAsessmentDao.builder()
				._id(ObjectId.get())
				.by(userId)
				.image(new ObjectId(photoId))
				.label(label)
				.dt_created(new Timestamp(System.currentTimeMillis()))
				.build();
		
		imageAsessmentRepository.save(dao);
	}
}
