package com.spring.app.domain;

import lombok.Getter;
import lombok.Setter;

public class MediADTO {
	
	@Getter
	@Setter
	private String aidx, qidx, userid, content, writeday;
	
	@Getter
	@Setter
	private MediQDTO qdto;
	
	
	
	
	
	
	
}
