package ru.itmo.iad.photorecognize.telegram.response;

import java.io.InputStream;

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class PhotoResponse extends Response<String> {

	private final InputFile file;

	public PhotoResponse(InputStream fileStream, String fileName, String caption) {
		super(caption);

		file = new InputFile(fileStream, fileName);
	}
	
	public PhotoResponse(InputFile inputfile, String caption) {
		super(caption);

		file = inputfile;
	}

	public String getContent() {
		return responseContent;
	}

	@Override
	public void send(AbsSender sender, Long chatId) throws TelegramApiException {
		SendPhoto photo = new SendPhoto();
		
		photo.setPhoto(file);
		photo.setCaption(this.getContent());
		photo.setChatId(Long.toString(chatId));

		sender.execute(photo);
	}
}
