package com.spring.app.question.model;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.spring.app.domain.AddQnADTO;
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
	
	// 추가질답 조회
	List<AddQnADTO> addquestionView(String aidx);
	
	// 질문자 이름
	String getwriterName(String userid);
	
	// 병원 이름, 아이디
	Map<String, String> getAnswerName(String aidx);

}
