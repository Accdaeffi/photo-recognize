package ru.itmo.iad.photorecognize.telegram.response;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

// please refactor if this class is implemented idiomatically incorrectly

public class EditMessageCaptionResponse extends EditMessageResponse {

    public EditMessageCaptionResponse(String responseContent, int editingMessageId, InlineKeyboardMarkup replyKeyboard) {
        super(responseContent, editingMessageId, replyKeyboard);
    }

    @Override
    public void send(AbsSender sender, Long chatId) throws TelegramApiException {
        var outMsg = new EditMessageCaption();

        outMsg.setChatId(Long.toString(chatId));
        outMsg.setMessageId(editingMessageId);

        if (this.getContent() != null) {
            outMsg.setReplyMarkup(replyKeyboard);
        }

        outMsg.setCaption(this.getContent());

        sender.execute(outMsg);
    }

}
