package ru.itmo.iad.photorecognize.service;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;

import ru.itmo.iad.photorecognize.domain.dao.AsessorDao;
import ru.itmo.iad.photorecognize.domain.repository.AsessorRepository;

@Service
public class AsessorService {
	
	@Autowired
	AsessorRepository repository;
	
	public AsessorDao getOrCreateAsessor(User user) {
		Optional<AsessorDao> optionalAsessor = repository.findByTelegramId(user.getId().toString());
		
		if (optionalAsessor.isPresent()) {
			return optionalAsessor.get();
		} else {
			AsessorDao asessor = AsessorDao.builder()
					._id(ObjectId.get())
					.telegramId(user.getId().toString())
					.username(user.getUserName())
					.honeypotCount(0)
					.build();
			
			asessor = repository.save(asessor);
			return asessor;
		}
	}
	
	public void increaseHoneypotCounter(String telegramId) {
		Optional<AsessorDao> optionalAsessor = repository.findByTelegramId(telegramId.toString());
		AsessorDao asessor = optionalAsessor.get();
		
		asessor.setHoneypotCount(asessor.getHoneypotCount()+1);
		
		repository.save(asessor);
	}

}
