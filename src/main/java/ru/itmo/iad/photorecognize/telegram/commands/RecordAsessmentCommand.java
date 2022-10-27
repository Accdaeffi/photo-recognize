package ru.itmo.iad.photorecognize.telegram.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;

import ru.itmo.iad.photorecognize.domain.Label;
import ru.itmo.iad.photorecognize.service.AsessmentSaver;
import ru.itmo.iad.photorecognize.telegram.keyboards.ThanksKeyboard;
import ru.itmo.iad.photorecognize.telegram.response.EditMessageReplyMarkupResponse;
import ru.itmo.iad.photorecognize.telegram.response.Response;

@Service
@Scope("prototype")
public class RecordAsessmentCommand extends AbsCommand {

	@Autowired
	AsessmentSaver assessmentSaver;

	@Autowired
	ThanksKeyboard thanksKeyboard;

	private final User asessor;
	private final String photoId;
	private final Label label;
	private final boolean isHoneypot;
	private final int messageId;

	public RecordAsessmentCommand(User asessor, String argument, int messageId) throws Exception {
		this.asessor = asessor;
		String[] splittedArgument = argument.split(" ", 3);
		this.photoId = splittedArgument[0];
		this.label = Label.getByButtonCode(splittedArgument[1]);
		this.isHoneypot = Boolean.parseBoolean(splittedArgument[2]);
		this.messageId = messageId;
	}

	@Override
	public Response<?> execute() {
		assessmentSaver.saveAssessment(asessor.getId().toString(), photoId, label, isHoneypot);

		return new EditMessageReplyMarkupResponse(thanksKeyboard.getKeyboard(label), messageId);
	}

}
