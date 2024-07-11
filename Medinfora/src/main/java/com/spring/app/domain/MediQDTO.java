package com.spring.app.domain;

import lombok.Getter;
import lombok.Setter;

public class MediQDTO {
	
	@Getter
	@Setter
	private String qidx, userid, title, content, writeday, imgsrc, acount, open, viewCount;
	
	@Getter
	@Setter
	private MediADTO adto;
	
	
	
	
	
}
