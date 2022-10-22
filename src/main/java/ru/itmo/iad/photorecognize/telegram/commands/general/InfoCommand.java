package ru.itmo.iad.photorecognize.telegram.commands.general;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import ru.itmo.iad.photorecognize.telegram.commands.AbsCommand;
import ru.itmo.iad.photorecognize.telegram.exception.NotEnoughParametersException;
import ru.itmo.iad.photorecognize.telegram.response.Response;

@Service
@Scope("prototype")
public class InfoCommand extends AbsCommand {

	String label;

	public InfoCommand(String label) throws NotEnoughParametersException {
		if (label != null) {
			this.label = label;
		} else {
			throw new NotEnoughParametersException();
		}
	}

	@Override
	public Response<?> execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
