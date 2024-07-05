package com.spring.app.domain;

public class HolidayVO {

	private String sumary,date;

	public HolidayVO(String sumary, String date) {
		this.sumary = sumary;
		this.date = date;
	}

	public String getSumary() {
		return sumary;
	}

	public String getDate() {
		return date;
	}
	
}
