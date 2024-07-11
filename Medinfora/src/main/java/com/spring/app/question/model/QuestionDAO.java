package com.spring.app.question.model;

import org.apache.ibatis.annotations.Mapper;

import com.spring.app.domain.MediQDTO;

@Mapper
public interface QuestionDAO {
	
	// 질문등록
	int questionWriteEnd(MediQDTO qdto);

}
