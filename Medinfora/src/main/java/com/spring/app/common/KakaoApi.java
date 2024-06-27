package com.spring.app.common;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Repository;

@Repository
public class KakaoApi {
	
	private String kakaoApiKey = "d3af5a04acf31414f29cf45ae12d307a";
	private String redirectUri = "login/kakaoLogin.bibo";
	
	
	public String getAccessToken(String code) {
		
		String accessToken = "";
		String refreshToken = "";
		String sendURL = "https://kauth.kakao.com/oauth/token";
		
		
		try {
			
			URL url = new URL(sendURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			//필수 헤더 세팅
	        conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
	        conn.setDoOutput(true); //OutputStream으로 POST 데이터를 넘겨주겠다는 옵션.
	        
	        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
	        StringBuilder sb = new StringBuilder();
	        
	        sb.append("grant_type=authorization_code");
	        sb.append("&client_id=").append(kakaoApiKey);
	        sb.append("&redirect_uri=").append(redirectUri);
	        sb.append("&code=").append(code);			
			
	        bw.write(sb.toString());
	        bw.flush();
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return "";
	}
	
	
	
	
	
	
	
	
	
	public String getKakaoApiKey() {
		return kakaoApiKey;
	}
	public void setKakaoApiKey(String kakaoApiKey) {
		this.kakaoApiKey = kakaoApiKey;
	}
	public String getRedirectUri() {
		return redirectUri;
	}
	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

}
