package com.spring.app.question.service;

import java.util.List;
import java.util.Map;

import com.spring.app.domain.MediADTO;
import com.spring.app.domain.MediQDTO;

public interface QuestionService {
	
	// 질문등록
	int questionWriteEnd(MediQDTO qdto);
	
	// 전체 수(검색 포함)
	int totalquestion(Map<String, String> paraMap);
	
	// 전체 리스트(검색포함)
	List<MediQDTO> totalquestionList(Map<String, String> paraMap);
	
	// 질문 조회
	MediQDTO questionView(int qidx);
	
	// 답변조회
	List<MediADTO> answerView(int qidx);
	
	
	

	

}
