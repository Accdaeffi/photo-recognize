package ru.itmo.iad.photorecognize.telegram.keyboards;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import ru.itmo.iad.photorecognize.domain.Label;
import ru.itmo.iad.photorecognize.domain.ZeroLevelLabel;

@Service
public class LabelKeyboard {

	public InlineKeyboardMarkup getKeyboard(String photoId, ZeroLevelLabel zeroLevelLabel) {
		InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

		List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

		List<Label> labels = Stream.of(Label.values())
				.filter(l -> l.getLabelZeroLevel().equals(zeroLevelLabel))
				.collect(Collectors.toList());

		for (Label label : labels) {
			InlineKeyboardButton button = new InlineKeyboardButton();
			button.setText(label.getButtonText());
			button.setCallbackData(String.format("label %s %s", photoId, label.getButtonCode()));

			List<InlineKeyboardButton> row = new ArrayList<>();
			row.add(button);

			rowList.add(row);
		}

		InlineKeyboardButton button = new InlineKeyboardButton();
		button.setText("Назад");
		button.setCallbackData(String.format("back"));

		rowList.add(List.of(button));

		markup.setKeyboard(rowList);

		return markup;
	}

}
