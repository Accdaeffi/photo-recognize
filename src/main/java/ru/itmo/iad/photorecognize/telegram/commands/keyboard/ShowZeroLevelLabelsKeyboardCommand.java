package ru.itmo.iad.photorecognize.telegram.commands.keyboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import ru.itmo.iad.photorecognize.telegram.commands.AbsCommand;
import ru.itmo.iad.photorecognize.telegram.keyboards.ZeroLevelLabelKeyboard;
import ru.itmo.iad.photorecognize.telegram.response.EditMessageReplyMarkupResponse;
import ru.itmo.iad.photorecognize.telegram.response.Response;

@Service
@Scope("prototype")
public class ShowZeroLevelLabelsKeyboardCommand extends AbsCommand {

	@Autowired
	ZeroLevelLabelKeyboard zeroLevelLabelKeyboard;

	private final String photoId;
	private final int messageId;
	private final boolean isHoneypot;

	public ShowZeroLevelLabelsKeyboardCommand(String argument, int messageId) {

		String[] splittedArgument = argument.split(" ", 2);
		this.photoId = splittedArgument[0];
		this.isHoneypot = Boolean.parseBoolean(splittedArgument[1]);
		this.messageId = messageId;
	}

	@Override
	public Response<?> execute() {
		InlineKeyboardMarkup keyboard = zeroLevelLabelKeyboard.getKeyboard(photoId, isHoneypot);
		return new EditMessageReplyMarkupResponse(keyboard, messageId);
	}

}
