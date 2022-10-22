package ru.itmo.iad.photorecognize.telegram.commands;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import ru.itmo.iad.photorecognize.telegram.response.Response;

@Service
@Scope("prototype")
public class NextImageCommand extends AbsCommand {

	@Override
	public Response<?> execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
