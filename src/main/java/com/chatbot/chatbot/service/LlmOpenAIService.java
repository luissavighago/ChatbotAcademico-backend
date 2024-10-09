package com.chatbot.chatbot.service;

import com.chatbot.chatbot.enums.PromptTemplateEnum;
import com.chatbot.chatbot.exception.LlmServiceException;
import com.chatbot.chatbot.repository.PGVectorRepository;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LlmOpenAIService {

    @Autowired
    private PGVectorRepository pgVectorRepository;

    private final OpenAiChatModel chatModel;

    public LlmOpenAIService(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String call(String question) {
        return callModel(createPrompt(question));
    }

    public String call(String question, String chatHistory) {
        return callModel(createPrompt(question, chatHistory));
    }

    private String callModel(Prompt prompt) {
        ChatResponse chatResponse = executeChatModel(prompt);
        String answer = chatResponse.getResult().getOutput().getContent();
        if(answer == null  || answer.isBlank()) {
            throw new LlmServiceException("Falha ao obter a resposta da API da OpenAI");
        }
        return answer;
    }

    private ChatResponse executeChatModel(Prompt prompt) {
        try {
            return chatModel.call(prompt);
        }catch (Exception e) {
            throw new LlmServiceException("Falha ao acessar a API da OpenAI");
        }
    }

    private Prompt createPrompt(String question) {
        List<Document> results = searchSimilarity(question);
        PromptTemplate promptTemplate = new PromptTemplate(
                PromptTemplateEnum.DEFAULT.getTemplate(),
                Map.of("information", getDocumentInformationMessage(results), "question", question)
        );
        return promptTemplate.create();
    }

    private Prompt createPrompt(String question, String chatHistory) {
        List<Document> results = searchSimilarity(question);
        PromptTemplate promptTemplate = new PromptTemplate(
                PromptTemplateEnum.CHAT_HISTORY.getTemplate(),
                Map.of(
                        "chat_history",chatHistory,
                        "information", getDocumentInformationMessage(results),
                        "question", question)
        );
        return promptTemplate.create();
    }

    private List<Document> searchSimilarity(String question) {
        return pgVectorRepository.searchSimilarity(SearchRequest.query(question).withTopK(2));
    }

    private String getDocumentInformationMessage(List<Document> results) {
        return results.stream().map(Document::getContent).reduce("", String::concat);
    }
}
