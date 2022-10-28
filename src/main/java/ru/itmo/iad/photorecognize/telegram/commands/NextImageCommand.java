package ru.itmo.iad.photorecognize.telegram.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;

import lombok.extern.slf4j.Slf4j;
import ru.itmo.iad.photorecognize.domain.dao.AssessorDao;
import ru.itmo.iad.photorecognize.domain.dto.ImageDto;
import ru.itmo.iad.photorecognize.service.AsessorService;
import ru.itmo.iad.photorecognize.service.ImageGetter;
import ru.itmo.iad.photorecognize.telegram.keyboards.ZeroLevelLabelKeyboard;
import ru.itmo.iad.photorecognize.telegram.response.PhotoResponse;
import ru.itmo.iad.photorecognize.telegram.response.Response;
import ru.itmo.iad.photorecognize.telegram.response.StringResponse;

@Service
@Scope("prototype")
@Slf4j
public class NextImageCommand extends AbsCommand {

	@Autowired
	ImageGetter imageGetter;

	@Autowired
	AsessorService asessorService;

	@Autowired
	ZeroLevelLabelKeyboard zeroLevelLabelKeyboard;
	
	private final User user;
	
	public NextImageCommand(User user) {
		this.user = user;
	}

	@Override
	public Response<?> execute() {
		try {
			AssessorDao asessor = asessorService.getOrCreateAsessor(user);
			ImageDto image = imageGetter.getImage(asessor.getHoneypotCount());

			return new PhotoResponse(image.getData(), image.getPhotoId(), null,
					zeroLevelLabelKeyboard.getKeyboard(image.getPhotoId(), image.isHoneypot()));
		} catch (Exception e) {
			log.error("Ошибка!", e);
			return new StringResponse("Ошибка получения фото!");
		}
	}

}
