package ru.itmo.iad.photorecognize.telegram.commands;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.telegram.telegrambots.meta.api.objects.User;

import ru.itmo.iad.photorecognize.domain.Label;
import ru.itmo.iad.photorecognize.service.AsessmentSaver;
import ru.itmo.iad.photorecognize.telegram.response.EditMessageResponse;
import ru.itmo.iad.photorecognize.telegram.response.Response;

@Service
@Scope("prototype")
public class RecordAsessmentCommand extends AbsCommand {

	@Autowired 
	AsessmentSaver assessmentSaver;
	
	private final User asessor;
	private final String photoId;
	private final Label label;
	private final int messageId;
	
	public RecordAsessmentCommand(User asessor, String argument, int messageId) throws Exception {
		this.asessor = asessor;
		String[] splittedArgument = argument.split(" ", 2);
		this.photoId = splittedArgument[0];
		this.label = Label.getByButtonCode(splittedArgument[1]);
		this.messageId = messageId;
	}
	
	@Override
	public Response<?> execute() {
		assessmentSaver.saveAssessment(asessor.getId().toString(), photoId, label);

		return new EditMessageResponse("Спасибо за оценку!", messageId, null);
	}

}
