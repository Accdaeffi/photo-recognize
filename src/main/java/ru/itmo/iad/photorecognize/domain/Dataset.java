package ru.itmo.iad.photorecognize.domain;

public enum Dataset {
	EDUCATION("train"), 
	VALIDATION("test");

	String code;

	private Dataset(String code) {
		this.code = code;
	}
	
	public String toString() {
		return this.code;
	}
}
