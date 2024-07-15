package com.spring.app.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class MediQDTO {
	
	
	private String qidx, userid, subject, title, content, writeday, imgsrc, acount, open, viewCount, pwd;
	
	private MultipartFile filesrc;
	
	private MediADTO adto;
	
	
	
	private String newwrite;	// 0 새로운거  1 오늘날짜보다 지난거
	

	public String getNewwrite() {
		
		try {
			Date now = new Date(); // 현재시각
			SimpleDateFormat sdfmt = new SimpleDateFormat("yyyy-MM-dd");
			String str_now = sdfmt.format(now);
			
			now = sdfmt.parse(str_now);
			Date now_birthday = sdfmt.parse(writeday);
			
			if(now_birthday.before(now)) {
			// 회원의 올해생일이 현재날짜 보다 이전이라면
				newwrite = "1";
			}
			else {
			// 회원의 올해생일이 현재날짜 보다 이후이라면
			//	System.out.println("~~~~ 생일 아직 지나지 않음");
				newwrite = "0";
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return newwrite;
	}
	

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getImgsrc() {
		return imgsrc;
	}

	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}

	public MultipartFile getFilesrc() {
		return filesrc;
	}

	public void setFilesrc(MultipartFile filesrc) {
		this.filesrc = filesrc;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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


	public String getAcount() {
		return acount;
	}

	public void setAcount(String acount) {
		this.acount = acount;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getViewCount() {
		return viewCount;
	}

	public void setViewCount(String viewCount) {
		this.viewCount = viewCount;
	}

	public MediADTO getAdto() {
		return adto;
	}

	public void setAdto(MediADTO adto) {
		this.adto = adto;
	}
	
	
	
	
	
	
}
