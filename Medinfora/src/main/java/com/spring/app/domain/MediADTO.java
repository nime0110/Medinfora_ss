package com.spring.app.domain;


public class MediADTO {
	

	private String aidx, qidx, userid, content, writeday;
	
	private MediQDTO qdto;

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

	public MediQDTO getQdto() {
		return qdto;
	}

	public void setQdto(MediQDTO qdto) {
		this.qdto = qdto;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
