package com.spring.app.question.service;

import java.util.List;
import java.util.Map;

import com.spring.app.domain.AddQnADTO;
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
	
	// 답변작성
	int answerWrite(MediADTO mdto);
	
	// 추가질답 등록
	int addqaUpload(AddQnADTO addqadto);
	
	// 추가질답 수정
	int addqaUpdate(AddQnADTO addqadto);
	
	// 추가질답 삭제
	int addqaDelete(AddQnADTO addqadto);
	
	// 답변 수정
	int answerUpdate(MediADTO adto);
	
	// 답변 삭제
	int answerDelete(MediADTO adto);
	
	// 질문 삭제
	int questionDelete(String qdto);
	
	// 질문 수정
	int questionUpdate(MediQDTO qdto);
	
	// 조회수 증가
	void viewCountIncrease(int qidx);
	
	// 메인페이지 FAQ 보여줄 질문리스트
	List<MediQDTO> getQuestion();
	
	// 메인페이지 FAQ 보여줄 답변
	String getAnswer(String string);
	
	
	

	

}
