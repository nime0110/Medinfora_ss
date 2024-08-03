package com.spring.app.news.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.app.domain.NewsDTO;
import com.spring.app.news.model.NewsDAO;

@Service
public class NewsService {

	@Autowired
	private NewsDAO dao;
	
	public int totalcount() {
		return dao.totalcount();
	}

	public List<NewsDTO> getndtolist(Map<String, Integer> listparaMap) {
		return dao.getndtolist(listparaMap);
	}

	public NewsDTO getndto(int nidx) {
		return dao.getndto(nidx);
	}

}
