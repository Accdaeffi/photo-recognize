package ru.itmo.iad.photorecognize.telegram.commands.keyboard;

import org.jvnet.hk2.annotations.Service;
import org.springframework.context.annotation.Scope;

import ru.itmo.iad.photorecognize.telegram.commands.AbsCommand;
import ru.itmo.iad.photorecognize.telegram.response.Response;

@Service
@Scope("prototype")
public class ShowZeroLevelLabelsKeyboardCommand extends AbsCommand {

	private final String photoId;
	private final int messageId;

	public ShowZeroLevelLabelsKeyboardCommand(String photoId, int messageId) {
		this.photoId = photoId;
		this.messageId = messageId;
	}

	@Override
	public Response<?> execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
