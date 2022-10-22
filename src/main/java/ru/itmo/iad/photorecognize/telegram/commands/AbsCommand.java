package ru.itmo.iad.photorecognize.telegram.commands;

import ru.itmo.iad.photorecognize.telegram.response.Response;

public abstract class AbsCommand {
	
	public abstract Response<?> execute();
}
