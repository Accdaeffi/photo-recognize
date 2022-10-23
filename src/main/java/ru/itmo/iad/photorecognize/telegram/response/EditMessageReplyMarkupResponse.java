package ru.itmo.iad.photorecognize.telegram.response;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class EditMessageReplyMarkupResponse extends Response<InlineKeyboardMarkup> {

	private int editingMessageId;

	public EditMessageReplyMarkupResponse(InlineKeyboardMarkup responseContent, int editingMessageId) {
		super(responseContent);
		this.editingMessageId = editingMessageId;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void send(AbsSender sender, Long chatId) throws TelegramApiException {

		EditMessageReplyMarkup outMsg = new EditMessageReplyMarkup();

		outMsg.setChatId(Long.toString(chatId));
		outMsg.setMessageId(editingMessageId);

		outMsg.setReplyMarkup(this.getContent());

		sender.execute(outMsg);
	}

}
