package com.spring.app.domain;

import java.util.List;

public class MediADTO {
	

	private String aidx, qidx, userid, content, writeday, qnacnt;
	
	
	public String getQnacnt() {
		return qnacnt;
	}

	public void setQnacnt(String qnacnt) {
		this.qnacnt = qnacnt;
	}

	private List<AddQnADTO> addqnadtoList;
	

	public List<AddQnADTO> getAddqnadtoList() {
		return addqnadtoList;
	}

	public void setAddqnadtoList(List<AddQnADTO> addqnadtoList) {
		this.addqnadtoList = addqnadtoList;
	}

	public String getAidx() {
		return aidx;
	}

	public void setAidx(String aidx) {
		this.aidx = aidx;
	}

	public String getQidx() {
		return qidx;
	}

	public void setQidx(String qidx) {
		this.qidx = qidx;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriteday() {
		return writeday;
	}

	public void setWriteday(String writeday) {
		this.writeday = writeday;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
}
