package org.chatllm.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import org.chatllm.pojo.JavaConcept;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GeminiModel {

    public GeminiModel() throws JsonProcessingException {
        String gemini_api_key = System.getenv("GEMINI_API_KEY");
        Scanner scanner = new Scanner(System.in);

        //Creating chat model
        ChatModel model = GoogleAiGeminiChatModel.builder()
                .apiKey(gemini_api_key)
                .modelName("gemini-3.5-flash-lite")
                .build();

        // Creating chat memory
        MessageWindowChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(20);

        //System Message
        SystemMessage systemMessage = SystemMessage.from(
                """
                          You are an expert Java teacher
                          Explain Java concepts in simple language
                          with easy examples
                        """
        );

        chatMemory.add(systemMessage);

        for (int i = 1; i <= 2; i++) {

            PromptTemplate promptTemplate = PromptTemplate.from(
                    """
                            Explain {{concept}} in {{language}} for a {{level}} learner.
                            Return the response only as valid JSON with these fields:
                            {
                              "concept": "...",
                              "language": "...",
                              "level": "..."
                            }
                            """
            );

            System.out.println("THE PROMPT TEMPLATE IS : \n");
            System.out.println(promptTemplate.template());

            System.out.println("Concept : ");
            String concept = scanner.nextLine();

            System.out.println("language : ");
            String language = scanner.nextLine();

            System.out.println("level : ");
            String level = scanner.nextLine();

            Map<String, Object> variables = new HashMap<>();
            variables.put("concept", concept);
            variables.put("language", language);
            variables.put("level", level);

            Prompt prompt = promptTemplate.apply(variables);

            System.out.println(prompt.text());

            //User Message
            UserMessage userMessage = prompt.toUserMessage();

            chatMemory.add(userMessage);

            ChatResponse response = model.chat(chatMemory.messages());

            AiMessage aiMessage = response.aiMessage();

            ObjectMapper objectMapper = new ObjectMapper();

            JavaConcept javaConcept =
                    objectMapper.readValue(
                            aiMessage.text(),
                            JavaConcept.class
                    );
            System.out.println(javaConcept);
            chatMemory.add(aiMessage);
        }

        for (ChatMessage message : chatMemory.messages()) {
            System.out.println("THe chat Messages are : ");
            System.out.println(message);
            System.out.println("========================================");
        }

    }
}
