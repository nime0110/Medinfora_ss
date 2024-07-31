package com.spring.app.chatgpt.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class ChatGptService {

	@Value("${openai.api.key}")
    private String openaiApiKey;

	// 채팅 대화에 대한 모델
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
    private final OkHttpClient client = new OkHttpClient();
    
    // 대답 받기
    public String getGptResponse(String message) {
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        String jsonString = "{\"model\":\"gpt-4o-mini\",\"messages\":[{\"role\":\"user\",\"content\":\"" + message + "\"}]}";

        // HTTP 요청한 값 받기
        RequestBody body = RequestBody.create(jsonString.getBytes(StandardCharsets.UTF_8), JSON);
        
        // 받은 값의 객체 생성
        Request request = new Request.Builder()
                .url(OPENAI_API_URL)
                .post(body)
                .addHeader("Authorization", "Bearer " + openaiApiKey)	// http 요청에 header 추가 -> API 인증
                .build();	// 보낼 값 설정 완료

        try {
        	// 질문에 대한 대답 요청
        	Response response = client.newCall(request).execute();
        	String answer = response.body().string();
        	return answer;
        	/*
        		결과값이 잘 나오지 않을 경우 -> 아래 메소드를 진행했을 때 429 에러가 뜨면 OPEN AI 접속하여 결제를 확인해봐야함.
            return executeRequestWithRetry(request, 5); 	// 최대 5번 재시도
        } catch (IOException | InterruptedException e) {

        	 */
        } catch (IOException e) {
            e.printStackTrace();
            return "에러메시지 : " + e.getMessage();
        }
    }	// end of public String getGptResponse(String message) {----------------------------

/*
    // 질문에 대한 대답 요청
    private String executeRequestWithRetry(Request request, int retries) throws IOException, InterruptedException {
        int backoffTime = 2; 	// 초기 대기 시간 (초)
        
        for (int i = 1; i <= retries; i++) {
        	
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {	// 성공한 경우
                    return response.body().string();	// 결과값 return
                } 
                else if (response.code() == 429) {	// 429 에러 : 너무 많은 요청을 보냄
                    String retryAfter = response.header("Retry-After");
                    if (retryAfter != null) {
                        backoffTime = Integer.parseInt(retryAfter);
                    }
                    System.out.println("429 시도횟수 " +i + " / " + retries);
                    System.out.println("경과시간 " + backoffTime + "초");
                    TimeUnit.SECONDS.sleep(backoffTime);
                    backoffTime *= 2; 		// 백오프 시간 증가
                } else {
                    throw new IOException("Unexpected code " + response);
                }
            }
            
        }	// end of for-------------------------
        
        throw new IOException("시도횟수 초과");
    }	// end of private String executeRequestWithRetry(Request request, int retries) throws IOException, InterruptedException {----
*/
    
}
