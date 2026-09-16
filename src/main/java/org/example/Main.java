package org.example;


import dev.langchain4j.data.message.*;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;


import java.util.HashMap;
import java.util.List;
import java.util.Map;



class Main {

    public static void main(String[] args) {

        String gemini_api_key = System.getenv("GEMINI_API_KEY");

        //Creating chat model
        ChatModel model = GoogleAiGeminiChatModel.builder()
                .apiKey(gemini_api_key)
                .modelName("gemini-3.6-flash")
                .build();

        //System Message
        SystemMessage systemMessage = SystemMessage.from(
                """
                       You are an expert Java teacher
                       Explain Java concepts in simple language
                       with easy examples
                     """
        );

        //Prompt Template

        PromptTemplate promptTemplate = PromptTemplate.from(
                "What is {{topic}} in Java?"
        );

        Map<String, Object> variables = new HashMap<>();
        variables.put("topic", "Polymorphism");

        Prompt prompt = promptTemplate.apply(variables);

        System.out.println("THE PROMPT IS : " + prompt);

        //User Message
        UserMessage userMessage = UserMessage.from(prompt.text());

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