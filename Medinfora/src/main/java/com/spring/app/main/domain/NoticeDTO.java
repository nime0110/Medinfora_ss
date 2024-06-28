package com.spring.app.main.domain;

public class NoticeDTO {

	private int NIDX; //  글번호
	private String USERID; // 아이디
	private String TITLE; // 공지제목
	private String CONTENT; //공지내용
	private int VIEWCNT; // 조회수
	private String WRITEDAY; // 작성일
	private String FILENAME; // 첨부파일명
	private String ORGNAME; // 원본 파일명
	private String FILESIZE; // 파일크기
	
	
	
	
	/////////////////////////////////////////
	
	public int getNIDX() {
		return NIDX;
	}
	public void setNIDX(int nIDX) {
		NIDX = nIDX;
	}
	public String getUSERID() {
		return USERID;
	}
	public void setUSERID(String uSERID) {
		USERID = uSERID;
	}
	public String getTITLE() {
		return TITLE;
	}
	public void setTITLE(String tITLE) {
		TITLE = tITLE;
	}
	public String getCONTENT() {
		return CONTENT;
	}
	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}
	public int getVIEWCNT() {
		return VIEWCNT;
	}
	public void setVIEWCNT(int vIEWCNT) {
		VIEWCNT = vIEWCNT;
	}
	public String getWRITEDAY() {
		return WRITEDAY;
	}
	public void setWRITEDAY(String wRITEDAY) {
		WRITEDAY = wRITEDAY;
	}
	public String getFILENAME() {
		return FILENAME;
	}
	public void setFILENAME(String fILENAME) {
		FILENAME = fILENAME;
	}
	public String getORGNAME() {
		return ORGNAME;
	}
	public void setORGNAME(String oRGNAME) {
		ORGNAME = oRGNAME;
	}
	public String getFILESIZE() {
		return FILESIZE;
	}
	public void setFILESIZE(String fILESIZE) {
		FILESIZE = fILESIZE;
	}
	
	/////////////////////////////////////////
	
	
	
	
} // end of public class NoticeDTO
