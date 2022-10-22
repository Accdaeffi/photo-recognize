package ru.itmo.iad.photorecognize.telegram.commands.general;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import ru.itmo.iad.photorecognize.telegram.commands.AbsCommand;
import ru.itmo.iad.photorecognize.telegram.response.StringResponse;

@Service
@Scope("prototype")
public class HelpCommand extends AbsCommand {
	
	public HelpCommand() {}

	@Override
	public StringResponse execute() {
		
		StringBuilder builder = new StringBuilder();
		builder.append("Описание классов с примерами:");
		builder.append(System.lineSeparator());
		builder.append(System.lineSeparator());
		builder.append("ссылки ещё нет");
		
		return new StringResponse(builder.toString());
	}

}
