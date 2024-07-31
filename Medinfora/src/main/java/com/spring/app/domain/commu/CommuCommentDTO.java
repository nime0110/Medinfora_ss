package com.spring.app.domain.commu;

public class CommuCommentDTO {

	// 글번호, 유저아이디, 내용, 작성일자, 수정일자, 대댓글그룹번호, 뎁스(공간 띄우기용), 대댓글번호, 대댓글구분번호
	private String cidx, userid, content, writeday, updateday, groupno, depthno, fk_cmidx, cmidx;

	
	// getter / setter
	public String getCidx() {
		return cidx;
	}

	public void setCidx(String cidx) {
		this.cidx = cidx;
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

	public String getUpdateday() {
		return updateday;
	}

	public void setUpdateday(String updateday) {
		this.updateday = updateday;
	}

	public String getGroupno() {
		return groupno;
	}

	public void setGroupno(String groupno) {
		this.groupno = groupno;
	}

	public String getDepthno() {
		return depthno;
	}

	public void setDepthno(String depthno) {
		this.depthno = depthno;
	}

	public String getFk_cmidx() {
		return fk_cmidx;
	}

	public void setFk_cmidx(String fk_cmidx) {
		this.fk_cmidx = fk_cmidx;
	}

	public String getCmidx() {
		return cmidx;
	}

	public void setCmidx(String cmidx) {
		this.cmidx = cmidx;
	}
	
}
