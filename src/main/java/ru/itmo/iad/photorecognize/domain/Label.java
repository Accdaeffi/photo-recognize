package ru.itmo.iad.photorecognize.domain;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Label {
	SHOPPING("indoor/shopping and dining", "shopping", "Магазин или столовая", ZeroLevelLabel.INDOOR),
	WORKPLACE("indoor/workplace", "workplace", "Рабочее место", ZeroLevelLabel.INDOOR),
	HOME("indoor/home or hotel", "home", "Дом", ZeroLevelLabel.INDOOR);

	public String databaseCode;
	public String buttonCode;
	public String buttonText;
	public ZeroLevelLabel labelZeroLevel;

	public String toString() {
		return this.databaseCode;
	}

	public Label getByButtonCode(String buttonCode) throws Exception {
		return Arrays.stream(Label.values())
				.filter(label -> label.getButtonCode().equals(buttonCode))
				.findFirst()
				.get(); 
	}
}
