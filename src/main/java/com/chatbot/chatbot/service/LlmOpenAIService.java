package com.chatbot.chatbot.service;

import com.chatbot.chatbot.dto.QuestionRecordDTO;
import com.chatbot.chatbot.enums.PromptTemplateEnum;
import com.chatbot.chatbot.exception.LlmServiceException;
import com.chatbot.chatbot.repository.PGVectorRepository;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.api.OpenAiApi;
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

    public String call(QuestionRecordDTO questionRecordDTO) {
        return callModel(createPrompt(questionRecordDTO.question()), questionRecordDTO.apiKey());
    }

    public String call(QuestionRecordDTO questionRecordDTO, String chatHistory) {
        return callModel(createPrompt(questionRecordDTO.question(), chatHistory), questionRecordDTO.apiKey());
    }

    private String callModel(Prompt prompt, String apiKey) {
        ChatResponse chatResponse = executeChatModel(prompt, apiKey);
        String answer = chatResponse.getResult().getOutput().getContent();
        if(answer == null  || answer.isBlank()) {
            throw new LlmServiceException("Falha ao obter a resposta da API da OpenAI");
        }
        return answer;
    }

    private ChatResponse executeChatModel(Prompt prompt, String apiKey) {
        try {
            OpenAiChatModel chatModel;
            if(apiKey == null || apiKey.isEmpty()) {
                chatModel = this.chatModel;
            }else {
                chatModel = new OpenAiChatModel(new OpenAiApi(apiKey));
            }

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
        List<Document> results = searchSimilarity(question + chatHistory);
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
        return pgVectorRepository.searchSimilarity(
                SearchRequest.defaults()
                        .withQuery(question)
                        .withTopK(8)
                        .withSimilarityThreshold(0.7)
        );
    }

    private String getDocumentInformationMessage(List<Document> results) {
        return results.stream().map(Document::getContent).reduce("", String::concat);
    }
}
