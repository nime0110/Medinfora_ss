package com.spring.app.domain;

import java.util.Objects;

public class ReserveDTO {

	private String ridx;			// 예약번호
	private String userid;			// 아이디
	private String hidx;			// 병원인덱스
	private String reportday;		// 예약신청일
	private String checkin;			// 예약일
	private String rcode;			// 예약코드
	
	////////////////////////////////////////////////////
	
	// === Select 용 === //
	private String name;			// 환자이름
	private String mobile;			// 환자번호
	private String rStatus;			// 예약상태
	
	/////////////////////////////////////////////////////

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
	
//////////////////////////////////////////////////////////////////
	
	// === Select 용 Getter, Setter === //
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getrStatus() {
		return rStatus;
	}

	public void setrStatus(String rStatus) {
		this.rStatus = rStatus;
	}
	
//////////////////////////////////////////////////////////////////
	
	// === 리스트 중복제거용 === //
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReserveDTO that = (ReserveDTO) o;
        return Objects.equals(ridx, that.ridx);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ridx);
    }
	
}
