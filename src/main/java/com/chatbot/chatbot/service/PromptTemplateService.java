package com.chatbot.chatbot.service;

import com.chatbot.chatbot.enums.PromptTechniqueEnum;
import com.chatbot.chatbot.enums.PromptTemplateEnum;
import com.chatbot.chatbot.repository.PGVectorRepository;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PromptTemplateService {

    @Autowired
    private PGVectorRepository pgVectorRepository;

    public Prompt createPromptDefault(String question) {
        List<Document> results = searchSimilarity(question);
        PromptTemplate promptTemplate = new PromptTemplate(
                PromptTemplateEnum.DEFAULT.getTemplate(),
                Map.of("information", getDocumentInformationMessage(results), "question", question)
        );
        return promptTemplate.create();
    }

    public Prompt createPromptDefault(String question, String chatHistory) {
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

    public Prompt createPromptByTechnique(String question, PromptTechniqueEnum technique, String context) {
        PromptTemplate promptTemplate = new PromptTemplate(
                getTemplateByTechnique(technique),
                Map.of("information", context, "question", question)
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

    private String getTemplateByTechnique(PromptTechniqueEnum technique) {
        switch (technique) {
            case ZERO_SHOT:
                return PromptTemplateEnum.ZERO_SHOT.getTemplate();
            case FEW_SHOTS:
                return PromptTemplateEnum.FEW_SHOTS.getTemplate();
            case CHAIN_OF_THOUGHT:
                return PromptTemplateEnum.CHAIN_OF_THOUGHT.getTemplate();
            default:
                return PromptTemplateEnum.DEFAULT.getTemplate();
        }
    }
}
