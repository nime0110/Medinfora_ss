package com.spring.app.news.model;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.spring.app.domain.NewsDTO;

@Mapper
public interface NewsDAO {

	int totalcount();

	List<NewsDTO> getndtolist(Map<String, Integer> listparaMap);

	NewsDTO getndto(int nidx);

	int delndto(int nidx);

}
