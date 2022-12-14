package ru.itmo.iad.photorecognize.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.itmo.iad.photorecognize.domain.dao.UserDao;
import ru.itmo.iad.photorecognize.domain.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository repository;
	
	public UserDao getOrCreateUser(User telegramUser) {
		Optional<UserDao> optionalAsessor = repository.findByTelegramId(telegramUser.getId().toString());
		
		if (optionalAsessor.isPresent()) {
			return optionalAsessor.get();
		} else {
			UserDao user = UserDao.builder()
					._id(ObjectId.get())
					.telegramId(telegramUser.getId().toString())
					.username(telegramUser.getUserName())
					.build();

			user = repository.save(user);
			return user;
		}
	}

}
