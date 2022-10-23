package ru.itmo.iad.photorecognize.telegram.commands.general;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import ru.itmo.iad.photorecognize.domain.Label;
import ru.itmo.iad.photorecognize.telegram.commands.AbsCommand;
import ru.itmo.iad.photorecognize.telegram.exception.NotEnoughParametersException;
import ru.itmo.iad.photorecognize.telegram.response.Response;
import ru.itmo.iad.photorecognize.telegram.response.StringResponse;
import ru.itmo.iad.photorecognize.telegram.texts.InfoTexts;

@Service
@Scope("prototype")
public class InfoCommand extends AbsCommand {

	Label label;

	public InfoCommand(String labelText) throws Exception {
		if (labelText != null) {
			this.label = Label.getByButtonText(labelText);
		} else {
			throw new NotEnoughParametersException();
		}
	}

	@Override
	public Response<?> execute() {
		StringResponse response = new StringResponse(InfoTexts.infoTexts.get(label));
		return response;
	}

}
