package ru.itmo.iad.photorecognize.telegram.commands;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;

import lombok.extern.slf4j.Slf4j;
import ru.itmo.iad.photorecognize.domain.Label;
import ru.itmo.iad.photorecognize.domain.dao.AsessorDao;
import ru.itmo.iad.photorecognize.domain.dto.ImageDto;
import ru.itmo.iad.photorecognize.service.AsessmentSaver;
import ru.itmo.iad.photorecognize.service.AsessorService;
import ru.itmo.iad.photorecognize.service.ImageGetter;
import ru.itmo.iad.photorecognize.telegram.keyboards.ThanksKeyboard;
import ru.itmo.iad.photorecognize.telegram.keyboards.ZeroLevelLabelKeyboard;
import ru.itmo.iad.photorecognize.telegram.response.EditMessageReplyMarkupResponse;
import ru.itmo.iad.photorecognize.telegram.response.MultiResponse;
import ru.itmo.iad.photorecognize.telegram.response.PhotoResponse;
import ru.itmo.iad.photorecognize.telegram.response.Response;
import ru.itmo.iad.photorecognize.telegram.response.StringResponse;

@Service
@Scope("prototype")
@Slf4j
public class RecordAsessmentCommand extends AbsCommand {

	@Autowired
	AsessmentSaver assessmentSaver;

	@Autowired
	ThanksKeyboard thanksKeyboard;
	
	@Autowired
	AsessorService asessorService;
	
	@Autowired
	ImageGetter imageGetter;
	
	@Autowired
	ZeroLevelLabelKeyboard zeroLevelLabelKeyboard;

	private final User user;
	private final String photoId;
	private final Label label;
	private final boolean isHoneypot;
	private final int messageId;

	public RecordAsessmentCommand(User user, String argument, int messageId) throws Exception {
		this.user = user;
		String[] splittedArgument = argument.split(" ", 3);
		this.photoId = splittedArgument[0];
		this.label = Label.getByButtonCode(splittedArgument[1]);
		this.isHoneypot = Boolean.parseBoolean(splittedArgument[2]);
		this.messageId = messageId;
	}

	@Override
	public Response<?> execute() {
		List<Response<?>> responses = new ArrayList<Response<?>>();
		assessmentSaver.saveAssessment(user.getId().toString(), photoId, label, isHoneypot);
		
		responses.add(new EditMessageReplyMarkupResponse(thanksKeyboard.getKeyboard(label), messageId));

		try {
			AsessorDao asessor = asessorService.getOrCreateAsessor(user);
			ImageDto image = imageGetter.getImage(asessor.getHoneypotCount());

			responses.add(new PhotoResponse(image.getData(), image.getPhotoId(), null,
					zeroLevelLabelKeyboard.getKeyboard(image.getPhotoId(), image.isHoneypot())));
		} catch (Exception e) {
			log.error("Ошибка!", e);
			responses.add(new StringResponse("Ошибка получения фото!"));
		}
		
		return new MultiResponse(responses);
		
	}

}
