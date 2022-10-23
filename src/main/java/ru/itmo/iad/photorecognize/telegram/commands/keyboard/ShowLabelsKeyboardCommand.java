package ru.itmo.iad.photorecognize.telegram.commands.keyboard;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import ru.itmo.iad.photorecognize.domain.ZeroLevelLabel;
import ru.itmo.iad.photorecognize.telegram.commands.AbsCommand;
import ru.itmo.iad.photorecognize.telegram.keyboards.LabelKeyboard;
import ru.itmo.iad.photorecognize.telegram.response.EditMessageResponse;
import ru.itmo.iad.photorecognize.telegram.response.Response;

@Service
@Scope("prototype")
public class ShowLabelsKeyboardCommand extends AbsCommand {

	@Autowired
	LabelKeyboard labelKeyboard;

	private final String photoId;
	private final ZeroLevelLabel zeroLevelLabel;
	private final int messageId;

	public ShowLabelsKeyboardCommand(String argument, int messageId) throws Exception {
		String[] splittedArgument = argument.split(" ", 2);
		this.photoId = splittedArgument[0];
		this.zeroLevelLabel = ZeroLevelLabel.getByButtonCode(splittedArgument[1]);
		this.messageId = messageId;
	}

	@Override
	public Response<?> execute() {
		InlineKeyboardMarkup keyboard = labelKeyboard.getKeyboard(photoId, zeroLevelLabel);
		return new EditMessageResponse(null, messageId, keyboard);
	}

}
