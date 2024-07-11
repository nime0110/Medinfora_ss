package com.spring.app.question.service;

import com.spring.app.domain.MediQDTO;

public interface QuestionService {
	
	// 질문등록
	int questionWriteEnd(MediQDTO qdto);

}
