package org.chatllm.tools;

import dev.langchain4j.agent.tool.Tool;

public class CalculatorTool {

    @Tool("Multiply two numbers")
    public int multiply(int a , int b) {
        return (a * b);
    }
}
