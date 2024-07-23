package com.spring.app.question.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.app.domain.AddQnADTO;
import com.spring.app.domain.MediADTO;
import com.spring.app.domain.MediQDTO;
import com.spring.app.domain.MemberDTO;
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
		
		String name = qdao.getwriterName(qdto.getUserid());
		qdto.setName(name);
		
		return qdto;
	}
	
	// 답변 조회
	@Override
	public List<MediADTO> answerView(int qidx) {
		List<MediADTO> adtoList = null;
		
		adtoList = qdao.answerView(qidx);
		
		// 가정은 다음과 같다.
		// 먼저 답변리스트를 받아온 후 답변 수 만큼 포문을 돌려서 답변에 추가질답이 있는지 확인 후 
		// 있다면, 추가질문을 받아온 후 셋팅해준다.
		try {
			if(adtoList.size() > 0 ) {
				
				for(int i=0; i<adtoList.size(); i++) {
					MediADTO adto = adtoList.get(i);
					
					List<AddQnADTO> addqnadtoList = qdao.addquestionView(adto.getAidx());

					adto.setAddqnadtoList(addqnadtoList);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("서비스에서 널포인트 발생");
		}
		return adtoList;
	}

	
	
	
	
	

}
