package ru.itmo.iad.photorecognize.telegram.keyboards;

import java.util.ArrayList;
import java.util.List;

import org.jvnet.hk2.annotations.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import ru.itmo.iad.photorecognize.domain.ZeroLevelLabel;

@Service
public class ZeroLevelLabelKeyboard {

	public InlineKeyboardMarkup getKeyboard(String photoId) {
		InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

		List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

		for (ZeroLevelLabel label : ZeroLevelLabel.values()) {
			InlineKeyboardButton button = new InlineKeyboardButton();
			button.setText(label.getButtonText());
			button.setCallbackData(String.format("zero_level_label %s %s", photoId, label.getButtonCode()));

			List<InlineKeyboardButton> row = new ArrayList<>();
			row.add(button);

			rowList.add(row);
		}

		markup.setKeyboard(rowList);

		return markup;
	}

}