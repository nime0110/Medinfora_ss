package com.spring.app.chatgpt.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.app.chatgpt.service.ChatGptService;

@RestController
@RequestMapping("/api/")
public class ChatGptController {

	@Autowired
    private ChatGptService gptService;

    @PostMapping("chat.bibo")
    public Map<String, String> chat(@RequestBody Map<String, String> request) {
        String message = request.get("message");
        String fullResponse = gptService.getGptResponse(message);
       
        // 내용만 받기
        String reply = extractContentFromResponse(fullResponse);
        
        Map<String, String> response = new HashMap<>();
        response.put("reply", reply);
        return response;
    }

    // 내용만 받기
	private String extractContentFromResponse(String fullResponse) {
		try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(fullResponse);
            return jsonNode.at("/choices/0/message/content").asText();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error extracting content from response";
        }
	}
}
