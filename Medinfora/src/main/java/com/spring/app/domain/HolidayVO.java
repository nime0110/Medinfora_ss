package com.spring.app.domain;

public class HolidayVO {

	private String summary,holiday_date;

	public HolidayVO(String summary, String holiday_date) {
		this.summary = summary;
		this.holiday_date = holiday_date;
	}

	public String getSummary() {
		return summary;
	}

	public String getHoliday_date() {
		return holiday_date;
	}
	
}
