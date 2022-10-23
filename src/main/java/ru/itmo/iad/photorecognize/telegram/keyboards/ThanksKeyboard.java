package ru.itmo.iad.photorecognize.telegram.keyboards;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import ru.itmo.iad.photorecognize.domain.Label;

@Service
public class ThanksKeyboard {

	public InlineKeyboardMarkup getKeyboard(Label label) {
		InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

		List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

		InlineKeyboardButton button = new InlineKeyboardButton();
		button.setText("Спасибо за идентификацию " + label.getButtonText() + "!");
		button.setCallbackData(String.format("finish"));

		rowList.add(List.of(button));

		markup.setKeyboard(rowList);

		return markup;
	}

}
