package com.spring.app.domain;

import org.springframework.web.multipart.MultipartFile;

public class NoticeDTO {

	private int nidx; // 글번호
    private String userid; // 아이디
    private String title; // 공지제목
    private String content; // 공지내용
    private int viewcnt; // 조회수
    private String writeday; // 작성일
    private String filename; // 첨부파일명
    private String orgname; // 원본 파일명
    private String filesize; // 파일크기

    private MultipartFile attach;
    
    private boolean isFileDeleted;
 

	public int getNidx() {
        return nidx;
    }

    public boolean isFileDeleted() {
		return isFileDeleted;
	}

	public void setFileDeleted(boolean isFileDeleted) {
		this.isFileDeleted = isFileDeleted;
	}

	public void setNidx(int nidx) {
        this.nidx = nidx;
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

    public int getViewcnt() {
        return viewcnt;
    }

    public void setViewcnt(int viewcnt) {
        this.viewcnt = viewcnt;
    }

    public String getWriteday() {
        return writeday;
    }

    public void setWriteday(String writeday) {
        this.writeday = writeday;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public MultipartFile getAttach() {

        return attach;
    }

    public void setAttach(MultipartFile attach) {
        this.attach = attach;
    }

	

	
	
} // end of public class NoticeDTO
