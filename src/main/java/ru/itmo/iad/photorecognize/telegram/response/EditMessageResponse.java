package ru.itmo.iad.photorecognize.telegram.response;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class EditMessageResponse extends StringResponse {

	private int editingMessageId;
	private InlineKeyboardMarkup replyKeyboard;

	public EditMessageResponse(String responseContent, int editingMessageId, InlineKeyboardMarkup replyKeyboard) {
		super(responseContent);
		this.editingMessageId = editingMessageId;
		this.replyKeyboard = replyKeyboard;
	}

	@Override
	public void send(AbsSender sender, Long chatId) throws TelegramApiException {
		EditMessageText outMsg = new EditMessageText();

		outMsg.setChatId(Long.toString(chatId));
		outMsg.setMessageId(editingMessageId);

		outMsg.setReplyMarkup(replyKeyboard);

		if (this.getContent() != null) {
			outMsg.setText(this.getContent());
		}

		sender.execute(outMsg);
	}

}
