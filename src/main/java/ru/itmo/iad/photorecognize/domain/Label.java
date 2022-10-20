package ru.itmo.iad.photorecognize.domain;

public enum Label {
	
	LIFT("lift"),
	HOUSE("house"),
	FIELD("field");
	
	public String code;
	
	Label(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
}
