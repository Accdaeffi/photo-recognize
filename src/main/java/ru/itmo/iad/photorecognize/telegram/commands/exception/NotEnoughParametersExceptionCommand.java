package ru.itmo.iad.photorecognize.telegram.commands.exception;

import org.jvnet.hk2.annotations.Service;
import org.springframework.context.annotation.Scope;

import ru.itmo.iad.photorecognize.telegram.commands.AbsCommand;
import ru.itmo.iad.photorecognize.telegram.response.Response;
import ru.itmo.iad.photorecognize.telegram.response.StringResponse;

@Service
@Scope("prototype")
public class NotEnoughParametersExceptionCommand extends AbsCommand {

	@Override
	public Response<?> execute() {
		StringBuilder builder = new StringBuilder();
		builder.append("Недостатоно аргументов для команды!");
		
		return new StringResponse(builder.toString());
	}

}
