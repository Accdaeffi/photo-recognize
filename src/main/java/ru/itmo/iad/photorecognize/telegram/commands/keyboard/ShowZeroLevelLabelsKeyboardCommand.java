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

	public ShowZeroLevelLabelsKeyboardCommand(String photoId, int messageId) {
		this.photoId = photoId;
		this.messageId = messageId;
	}

	@Override
	public Response<?> execute() {
		InlineKeyboardMarkup keyboard = zeroLevelLabelKeyboard.getKeyboard(photoId);
		return new EditMessageReplyMarkupResponse(keyboard, messageId);
	}

}
