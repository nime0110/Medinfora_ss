package com.spring.app.question.model;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.spring.app.domain.AddQnADTO;
import com.spring.app.domain.MediADTO;
import com.spring.app.domain.MediQDTO;
import com.spring.app.domain.MemberDTO;

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
	
	// 답변등록
	int answerWrite(MediADTO mdto);
	
	// 추가질답 등록
	int addqaUpload(AddQnADTO addqadto);
	
	// 추가질문등록에 따른 답변 qnacnt 증가
	void updateqnanum(String aidx);
	
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
	
	// 병원정보 가져오기
	List<MemberDTO> memberView(String userid);
	
	// 병원진료과목 수
	List<String> getClassSize(String userid);
		
	// 병원 진료과목
	String getClasscode(String classcode);
	
	

}
