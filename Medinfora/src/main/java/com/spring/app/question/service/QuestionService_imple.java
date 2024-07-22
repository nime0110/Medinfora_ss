package com.spring.app.question.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.app.domain.AddQDTO;
import com.spring.app.domain.MediADTO;
import com.spring.app.domain.MediQDTO;
import com.spring.app.question.model.QuestionDAO;

@Service
public class QuestionService_imple implements QuestionService {
	
	@Autowired
	private QuestionDAO qdao;
	
	// 질문등록
	@Override
	public int questionWriteEnd(MediQDTO qdto) {
		int n = qdao.questionWriteEnd(qdto);
		return n;
	}
	
	// 전체 수(검색포함)
	@Override
	public int totalquestion(Map<String, String> paraMap) {
		int totalquestion = qdao.totalquestion(paraMap);
		return totalquestion;
	}
	
	
	// 전체 리스트(검색포함?)
	@Override
	public List<MediQDTO> totalquestionList(Map<String, String> paraMap) {
		List<MediQDTO> qList = qdao.totalquestionList(paraMap);
		return qList;
	}
	
	// 글 조회
	@Override
	public MediQDTO questionView(int qidx) {
		MediQDTO qdto = null;
		
		qdto = qdao.questionView(qidx);
		
		return qdto;
	}
	
	// 답변 조회
	@Override
	public List<MediADTO> answerView(int qidx) {
		List<MediADTO> adtoList = qdao.answerView(qidx);
		return adtoList;
	}

	
	
	
	
	

}
