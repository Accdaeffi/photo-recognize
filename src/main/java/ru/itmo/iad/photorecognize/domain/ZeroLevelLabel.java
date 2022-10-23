package ru.itmo.iad.photorecognize.domain;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ZeroLevelLabel {
	INDOOR("indoor", "Внутри"), 
	OUTDOOR_NATURAL("outdoor_natural", "Природа"),
	OUTDOOR_MAN_MADE("outdoor_man_made", "Строения");

	private String buttonCode;
	private String buttonText;

	public static ZeroLevelLabel getByButtonCode(String buttonCode) throws Exception {
		return Arrays.stream(ZeroLevelLabel.values())
				.filter(label -> label.getButtonCode().equals(buttonCode))
				.findFirst()
				.get(); 
	}

}
