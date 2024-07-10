package com.spring.app.question.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.app.question.model.QuestionDAO;

@Service
public class QuestionService_imple implements QuestionService {
	
	@Autowired
	private QuestionDAO qdao;

}
