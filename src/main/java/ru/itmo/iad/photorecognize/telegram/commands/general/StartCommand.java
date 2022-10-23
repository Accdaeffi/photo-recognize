package ru.itmo.iad.photorecognize.telegram.commands.general;

import org.springframework.stereotype.Service;

import ru.itmo.iad.photorecognize.telegram.commands.AbsCommand;
import ru.itmo.iad.photorecognize.telegram.response.Response;
import ru.itmo.iad.photorecognize.telegram.response.StringResponse;

@Service
public class StartCommand extends AbsCommand {

	@Override
	public Response<?> execute() {
		
		
		StringBuilder builder = new StringBuilder();
		builder.append("Велком ту бот:");
		builder.append(System.lineSeparator());
		builder.append(System.lineSeparator());
		builder.append("ссылки ещё нет");
		
		return new StringResponse(builder.toString());
	}

}
