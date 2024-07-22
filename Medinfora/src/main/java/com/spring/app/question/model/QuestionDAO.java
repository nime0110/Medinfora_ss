package com.spring.app.question.model;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.spring.app.domain.MediADTO;
import com.spring.app.domain.MediQDTO;

@Mapper
public interface QuestionDAO {
	
	// 질문등록
	int questionWriteEnd(MediQDTO qdto);
	
	// 전체 수(검색포함)
	int totalquestion(Map<String, String> paraMap);
	
	// 전체 리스트(검색포함?)
	List<MediQDTO> totalquestionList(Map<String, String> paraMap);
	
	// 질문조회
	MediQDTO questionView(int qidx);
	
	// 답변조회
	List<MediADTO> answerView(int qidx);
	
	// 추가질문 조회
	List<Map<String, String>> addQList(String adix);

}
