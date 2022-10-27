package ru.itmo.iad.photorecognize.service;

import java.util.Date;

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
	
	@Autowired
	AsessorService asessorService;

	public void saveAssessment(String userId, String photoId, Label label, boolean isHoneypot) {
		ImageAsessmentDao dao = ImageAsessmentDao.builder()
				._id(ObjectId.get())
				.by(userId)
				.imageId(new ObjectId(photoId))
				.label(label.toString())
				.dtCreated(new Date())
				.build();

		imageAsessmentRepository.save(dao);
		
		if (isHoneypot) {
			asessorService.increaseHoneypotCounter(userId);
		}
	}
}
