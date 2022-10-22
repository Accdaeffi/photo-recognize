package ru.itmo.iad.photorecognize.telegram.commands;

import org.jvnet.hk2.annotations.Service;
import org.springframework.context.annotation.Scope;
import org.telegram.telegrambots.meta.api.objects.User;

import ru.itmo.iad.photorecognize.domain.Label;
import ru.itmo.iad.photorecognize.telegram.response.Response;

@Service
@Scope("prototype")
public class RecordAsessmentCommand extends AbsCommand {

	private final User asessor;
	private final String photoId;
	private final Label label;
	private final String messageId;
	
	public RecordAsessmentCommand(User asessor, String photoId, Label label, String messageId) {
		this.asessor = asessor;
		this.photoId = photoId;
		this.label = label;
		this.messageId = messageId;
	}
	
	@Override
	public Response<?> execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
