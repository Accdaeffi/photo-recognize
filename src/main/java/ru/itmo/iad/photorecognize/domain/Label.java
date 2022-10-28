package ru.itmo.iad.photorecognize.domain;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Label {
	SHOPPING("indoor/shopping and dining", "shopping", "Коммерция и общепит", ZeroLevelLabel.INDOOR),
	WORKPLACE("indoor/workplace", "workplace", "Место работы", ZeroLevelLabel.INDOOR),
	HOME("indoor/home or hotel", "home", "Жилое помещение", ZeroLevelLabel.INDOOR),
	IN_TRANSPORT("indoor/transportation", "in_transport", "Транспорт", ZeroLevelLabel.INDOOR),
	IN_SPORT("indoor/sports and leisure", "in_sport", "Спорт и развлечения", ZeroLevelLabel.INDOOR),
	IN_CULTURAL("indoor/cultural", "in_cultural", "Культура, образование, государство", ZeroLevelLabel.INDOOR),
	IN_AMB("indoor/ambiguous", "in_amb", "Неоднозначное", ZeroLevelLabel.INDOOR),
	
	WATER("outdoor natural/water, ice, snow", "water", "Вода", ZeroLevelLabel.OUTDOOR_NATURAL),
	LANDSCAPE("outdoor natural/mountains, hills, desert, sky", "landscape", "Рельеф или пустыни", ZeroLevelLabel.OUTDOOR_NATURAL),
	FOREST("outdoor natural/forest, field, jungle", "forest", "Леса и поля", ZeroLevelLabel.OUTDOOR_NATURAL),

	OUT_TRANSPORT("outdoor man-made/transportation", "out_transport", "Транспорт", ZeroLevelLabel.OUTDOOR_MAN_MADE),
	OUT_CULTURAL("outdoor man-made/cultural or historical building or place", "out_cultural", "Культурное, историческое, военное", ZeroLevelLabel.OUTDOOR_MAN_MADE),
	OUT_SPORT("outdoor man-made/sports fields, parks, leisure spaces", "out_sport", "Спорт, развлечения и парки", ZeroLevelLabel.OUTDOOR_MAN_MADE),
	INDUSTRY("outdoor man-made/industrial and construction", "industry", "Промышленность", ZeroLevelLabel.OUTDOOR_MAN_MADE),
	VILLAGE("outdoor man-made/houses, cabins, gardens, and farms", "village", "Загородные объекты", ZeroLevelLabel.OUTDOOR_MAN_MADE),
	CITY("outdoor man-made/commercial buildings, shops, markets, cities, and towns", "city", "Городские объекты", ZeroLevelLabel.OUTDOOR_MAN_MADE);

	public String databaseCode;
	public String buttonCode;
	public String buttonText;
	public ZeroLevelLabel labelZeroLevel;

	public String toString() {
		return this.databaseCode;
	}

	public static Label getByButtonCode(String buttonCode) throws Exception {
		return Arrays.stream(Label.values())
				.filter(label -> label.getButtonCode().equals(buttonCode))
				.findFirst()
				.get(); 
	}
	
	public static Label getByButtonText(String buttonText) throws Exception {
		return Arrays.stream(Label.values())
				.filter(label -> label.getButtonCode().toLowerCase().equals(buttonText.toLowerCase()))
				.findFirst()
				.get(); 
	}
}
