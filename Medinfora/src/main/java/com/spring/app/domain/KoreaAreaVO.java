package com.spring.app.domain;

public class KoreaAreaVO {

	private String city, local, country;

	public KoreaAreaVO(String city, String local) {
		this.city = city;
		this.local = local;
	}
	
	public KoreaAreaVO(String city, String local, String country) {
		this.city = city;
		this.local = local;
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public String getLocal() {
		return local;
	}

	public String getCountry() {
		return country;
	}
	
}
