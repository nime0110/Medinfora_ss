package com.spring.app.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.spring.app.common.KakaoApi;
import com.spring.app.main.service.MainService;

@Controller
public class LoginController {
	@Autowired
	private MainService service;
	
	@Autowired
	private KakaoApi KakaoApi;
	
	
}
