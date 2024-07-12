package com.spring.app.question.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
