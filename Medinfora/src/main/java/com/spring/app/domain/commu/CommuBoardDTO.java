package com.spring.app.domain.commu;

import org.springframework.web.multipart.MultipartFile;

public class CommuBoardDTO {
	// 글인덱스, 카테고리, 제목, 내용, 작성일자, 수정일자, 유저아이디, 공감수, 조회수, 댓글수
	private String cidx, category, title, content, writeday, updateday, 
				   userid, suggestioncnt, viewcnt, commentCount;
	
	// getter / setter
	public String getCidx() {
		return cidx;
	}

	public void setCidx(String cidx) {
		this.cidx = cidx;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	public String getUpdateday() {
		return updateday;
	}

	public void setUpdateday(String updateday) {
		this.updateday = updateday;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getSuggestioncnt() {
		return suggestioncnt;
	}

	public void setSuggestioncnt(String suggestioncnt) {
		this.suggestioncnt = suggestioncnt;
	}

	public String getViewcnt() {
		return viewcnt;
	}

	public void setViewcnt(String viewcnt) {
		this.viewcnt = viewcnt;
	}

	public String getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(String commentCount) {
		this.commentCount = commentCount;
	}



	
}
