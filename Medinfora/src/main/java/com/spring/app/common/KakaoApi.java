package com.spring.app.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


@Repository
public class KakaoApi {
	
	private String kakaoApiKey = "d3af5a04acf31414f29cf45ae12d307a";
	private String redirectUri = "login/kakaoLogin.bibo";
	
	private Logger log = LoggerFactory.getLogger(KakaoApi.class);
	
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
	        sb.append("&redirect_uri=http://localhost:9099/Medinfora/").append(redirectUri);
	        sb.append("&code=").append(code);			
			
	        bw.write(sb.toString());
	        bw.flush();
	        
	        
	        
	        int responseCode = conn.getResponseCode();
	        log.info("[KakaoApi.getAccessToken] responseCode = {}", responseCode);
			
	        BufferedReader br;
	        if (responseCode >= 200 && responseCode < 300) {
	            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        } else {
	            br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	        }
	        
	        String line = "";
	        StringBuilder responseSb = new StringBuilder();
	        while((line = br.readLine()) != null){
	            responseSb.append(line);
	        }
	        String result = responseSb.toString();
	        log.info("responseBody = {}", result);

	        JsonParser parser = new JsonParser();
	        JsonElement element = parser.parse(result);
	        accessToken = element.getAsJsonObject().get("access_token").getAsString();
	        refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();

	        br.close();
	        bw.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return accessToken;
	}
	
	
	// 카카오 로그인 유저 정보 구해오기
	public Map<String, Object> getUserInfo(String accessToken) {
		
		Map<String, Object> userInfo = new HashMap<>();
		
		String sendURL = "https://kapi.kakao.com/v2/user/me";
		
		try {
			
			URL url = new URL(sendURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Bearer " + accessToken);
	        conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
			
	        int responseCode = conn.getResponseCode();
	        log.info("[KakaoApi.getUserInfo] responseCode : {}",  responseCode);
			
	        BufferedReader br;
	        if (responseCode >= 200 && responseCode <= 300) {
	            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        } else {
	            br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	        }
	        
	        String line = "";
	        StringBuilder responseSb = new StringBuilder();
	        while((line = br.readLine()) != null){
	            responseSb.append(line);
	        }
	        String result = responseSb.toString();
	        log.info("responseBody = {}", result);

	        JsonParser parser = new JsonParser();
	        JsonElement element = parser.parse(result);
	        
	        // JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
	        JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
	        
	        // String nickname = properties.getAsJsonObject().get("nickname").getAsString();
	        String name = kakaoAccount.getAsJsonObject().get("name").getAsString();
	        String email = kakaoAccount.getAsJsonObject().get("email").getAsString();
	        
	        // 이메일 유효한지 확인 값 fales 면 유효하지 않음
	        String is_email_verified = kakaoAccount.getAsJsonObject().get("is_email_verified").getAsString();
	        
	        String birthyear = kakaoAccount.getAsJsonObject().get("birthyear").getAsString();
	        // YYYY 형식
	        
	        String birthday = kakaoAccount.getAsJsonObject().get("birthday").getAsString();
	        // MMDD 형식
	        
	        String gender = kakaoAccount.getAsJsonObject().get("gender").getAsString();
	        // male, female 둘만 나옴
	        
	        String phone_number = kakaoAccount.getAsJsonObject().get("phone_number").getAsString();
	        // 국내 번호인 경우 +82 00-0000-0000 형식
	        // 해외 번호인 경우 자릿수, 붙임표(-) 유무나 위치가 >> 해외번호 안할꺼임
	       
	        
	        // 계정 정보중 회원가입에 필요한 것만 가져온다.
	        
	        userInfo.put("name", name);
	        userInfo.put("email", email);
	        userInfo.put("is_email_verified", is_email_verified);
	        userInfo.put("birthyear", birthyear);
	        userInfo.put("birthday", birthday);
	        userInfo.put("gender", gender);
	        userInfo.put("phone_number", phone_number);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return userInfo;
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
