package com.neo_educ.backend.apps.english.llm.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class LLMService {

  private final ChatClient chatClient;

  public LLMService(ChatClient chatClient) {
    this.chatClient = chatClient;
  }

  public String chat(String userInput) {
    return chatClient.prompt(userInput).call().content();
  }
}
