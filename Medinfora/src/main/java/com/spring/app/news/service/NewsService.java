package com.spring.app.news.service;

import java.util.List;
import java.util.Map;

import com.spring.app.domain.NewsDTO;

public interface NewsService {

	public int totalcount();
	
	public List<NewsDTO> getndtolist(Map<String, Integer> listparaMap);
	
	public NewsDTO getndto(int nidx);
	
	public int del(int nidx);
	
}
