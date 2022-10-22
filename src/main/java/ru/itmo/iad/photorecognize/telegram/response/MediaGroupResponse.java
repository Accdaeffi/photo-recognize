package ru.itmo.iad.photorecognize.telegram.response;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MediaGroupResponse extends Response<List<InputFile>> {

	private final String caption;

	public MediaGroupResponse(List<InputFile> inputFiles, String caption) {
		super(inputFiles);
		this.caption = caption;
	}

	public List<InputFile> getContent() {
		return responseContent;
	}

	@Override
	public void send(AbsSender sender, Long chatId) throws TelegramApiException {
		SendMediaGroup mediaGroup = new SendMediaGroup();

		List<InputMedia> medias = new ArrayList<InputMedia>();
		boolean captionAdded = false;

		for (InputFile image : this.getContent()) {
			InputMediaPhoto photo = new InputMediaPhoto();

			photo.setMedia(image.getNewMediaFile(), image.getMediaName());

			if (!captionAdded) {
				photo.setCaption(caption);
				captionAdded = true;
			}

			medias.add(photo);

		}

		mediaGroup.setMedias(medias);
		mediaGroup.setChatId(Long.toString(chatId));
		sender.execute(mediaGroup);
	}

}
