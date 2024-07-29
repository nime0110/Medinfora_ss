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
    	
        String message = request.get("message");	// 사용자가 입력한 말
        
        String fullResponse = gptService.getGptResponse(message);	// GPT 에서 돌려주는 전체 값
        /*
        	System.out.println(fullResponse);
        	{
			  "id": "chatcmpl-9qE7JolAUuRiNyhNscjU8VTCLEwYR",
			  "object": "chat.completion",
			  "created": 1722234349,
			  "model": "gpt-3.5-turbo-0125",
			  "choices": [
			    {
			      "index": 0,
			      "message": {
			        "role": "assistant",
			        "content": "비만은 신체적인 건강 문제를 초래할 수 있는 심각한 상태로, 과다한 체중이나 지방이 체중과 비율이 맞지 않는 상태를 가리킵니다. 비만은 심혈관 질환, 당뇨병, 고혈압, 관절염, 수면무호흡증, 대표적으로 혈당이나 많은 지방의 혈중농도 등 다양한 질병의 위험 요인으로 작용할 수 있습니다. 식습관과 생활 습관을 개선하는 등의 노력을 통해 비만을 예방하고 치료할 수 있습니다. 만약에 비만에 대해 고민이 있는 경우에는 전문가의 도움을 받아 적절한 대처가 필요합니다."
			      },
			      "logprobs": null,
			      "finish_reason": "stop"
			    }
			  ],
			  "usage": {
			    "prompt_tokens": 9,
			    "completion_tokens": 247,
			    "total_tokens": 256
			  },
			  "system_fingerprint": null
			}
         */
        // 내용만 받기 위한 메소드 사용
        String reply = extractContentFromResponse(fullResponse);
        
        Map<String, String> response = new HashMap<>();
        response.put("reply", reply);
        return response;
    }	// end of public Map<String, String> chat(@RequestBody Map<String, String> request) {--------

    // 내용만 받기
	private String extractContentFromResponse(String fullResponse) {
		try {
            ObjectMapper objectMapper = new ObjectMapper();
            // json 타입으로 변환하여 읽기
            JsonNode jsonNode = objectMapper.readTree(fullResponse);
            // choices -> 첫번째 -> message -> content 의 값 return
            return jsonNode.at("/choices/0/message/content").asText();
        } catch (Exception e) {
            e.printStackTrace();
            return "◆ 오류 ◆";
        }
	}	// end of private String extractContentFromResponse(String fullResponse) {------
}
