package org.example;


import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

class Main {

    public static void main(String[] args) {

        System.out.println("Hi");

        String gemini_api_key = System.getenv("GEMINI_API_KEY");

        //Creating chat model

        ChatModel model = GoogleAiGeminiChatModel.builder()
                .apiKey(gemini_api_key)
                .modelName("gemini-3.6-flash")
                .build();


        //System Message

        SystemMessage systemMessage = SystemMessage.from(
                "You are an expert Java teacher" +
                        "Explain Java concepts in simple language" +
                        "with easy examples.");
        //User Message

        UserMessage userMessage = UserMessage.from(
                "What is polymorphism in Java?"
        );

        List<ChatMessage> messages = List.of(
                systemMessage,
                userMessage

        );

        ChatResponse response = model.chat(messages);

        System.out.println("RESPONSE FROM AI : \n" + response);

        AiMessage aiMessage = response.aiMessage();

        System.out.println("MESSAGE FROM AI \n " + aiMessage);

        System.out.println("ACTUAL RESPONSE : " + aiMessage.text());

    }
}