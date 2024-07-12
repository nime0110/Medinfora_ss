package com.spring.app.domain;

public class ReserveDTO {

	private String ridx;			// 예약번호
	private String userid;			// 아이디
	private String hidx;			// 병원인덱스
	private String reportday;		// 예약신청일
	private String checkin;			// 예약일
	private String rcode;			// 예약코드

	// === select 용 === //
	private String rStatus;			// 예약상태
	
	////////////////////////////////////////////////////
	
	// === Getter, Setter === //
	public String getRidx() {
		return ridx;
	}

	public void setRidx(String ridx) {
		this.ridx = ridx;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getHidx() {
		return hidx;
	}

	public void setHidx(String hidx) {
		this.hidx = hidx;
	}

	public String getReportday() {
		return reportday;
	}

	public void setReportday(String reportday) {
		this.reportday = reportday;
	}

	public String getCheckin() {
		return checkin;
	}

	public void setCheckin(String checkin) {
		this.checkin = checkin;
	}

	public String getRcode() {
		return rcode;
	}

	public void setRcode(String rcode) {
		this.rcode = rcode;
	}

	public String getrStatus() {
		return rStatus;
	}

	public void setrStatus(String rStatus) {
		this.rStatus = rStatus;
	}
	
	
}
