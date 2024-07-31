package com.spring.app.domain.commu;

import org.springframework.web.multipart.MultipartFile;

public class CommuFilesDTO {
	
	private MultipartFile attach; //commuWrite.jsp 파일에서 input type="file" 인 name 의 이름과 동일해야 함
	 
	private String cidx;		//시퀀스 채번
	private String fileName;     // WAS(톰캣)에 저장될 파일명(2024062609291535243254235235234.png)                                       
	private String orgname;  // 진짜 파일명(강아지.png)  // 사용자가 파일을 업로드 하거나 파일을 다운로드 할때 사용되어지는 파일명 
	private String fileSize;     // 파일크기  
	
	// getter / setter

	public MultipartFile getAttach() {
		return attach;
	}
	public void setAttach(MultipartFile attach) {
		this.attach = attach;
	}
	
	
	public String getCidx() {
		return cidx;
	}
	public void setCidx(String cidx) {
		this.cidx = cidx;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
	public String getOrgname() {
		return orgname;
	}
	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	
}
