package ru.itmo.iad.photorecognize.domain;

public enum Dataset {
	train("train"), 
	test("test");

	String code;

	private Dataset(String code) {
		this.code = code;
	}
	
	public String toString() {
		return this.code;
	}
}
