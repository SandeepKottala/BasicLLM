package org.example;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.model.input.PromptTemplate;

import java.util.HashMap;
import java.util.Map;

class Main {
    public static void main(String args[]) {

        //gemini api key

        String gemini_api_key = System.getenv("GEMINI_API_KEY");

        //Creating chat model

        ChatModel model = GoogleAiGeminiChatModel.builder()
                .apiKey(gemini_api_key)
                .modelName("gemini-3.6-flash")
                .build();

        //creating prompt template

        PromptTemplate promptTemplate = PromptTemplate.from(
                "Explain {{topic}} in simple words with examples"

        );

        Map<String, Object> variables = new HashMap<>();
        variables.put("topic", "Gen AI");

        String prompt = promptTemplate.apply(variables)
                .text();

        System.out.println("Prompt : " + prompt);

        String response = model.chat(prompt);

        System.out.println(response);




    }
}