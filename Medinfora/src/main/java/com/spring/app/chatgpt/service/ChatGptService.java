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

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
    private final OkHttpClient client = new OkHttpClient();
    
    public String getGptResponse(String message) {
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        String jsonString = "{\"model\":\"gpt-3.5-turbo\",\"messages\":[{\"role\":\"user\",\"content\":\"" + message + "\"}]}";

        RequestBody body = RequestBody.create(jsonString.getBytes(StandardCharsets.UTF_8), JSON);
        Request request = new Request.Builder()
                .url(OPENAI_API_URL)
                .post(body)
                .addHeader("Authorization", "Bearer " + openaiApiKey)
                .build();

        try {
            return executeRequestWithRetry(request, 5); // 최대 5번 재시도
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error communicating with GPT: " + e.getMessage();
        }
    }

    private String executeRequestWithRetry(Request request, int retries) throws IOException, InterruptedException {
        int backoffTime = 2; // 초기 대기 시간 (초)
        for (int attempt = 1; attempt <= retries; attempt++) {
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    return response.body().string();
                } else if (response.code() == 429) {
                    String retryAfter = response.header("Retry-After");
                    if (retryAfter != null) {
                        backoffTime = Integer.parseInt(retryAfter);
                    }
                    System.out.println("429 Too Many Requests. Attempt " + attempt + " of " + retries);
                    System.out.println("Waiting for " + backoffTime + " seconds before retrying...");
                    TimeUnit.SECONDS.sleep(backoffTime);
                    backoffTime *= 2; // 백오프 시간 증가
                } else {
                    System.out.println("Unexpected response code: " + response.code() + " - " + response.message());
                    throw new IOException("Unexpected code " + response);
                }
            }
        }
        throw new IOException("Exceeded max retries for request");
    }
}
