package com.chatbot.chatbot.service;

import com.chatbot.chatbot.dto.ChatDTO;
import com.chatbot.chatbot.enums.PromptTemplateEnum;
import com.chatbot.chatbot.models.ChatModel;
import com.chatbot.chatbot.repository.ChatRepository;
import com.chatbot.chatbot.repository.PGVectorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private PGVectorRepository pgVectorRepository;

    public void sendQuestion(String question) {
        ChatDTO chatDTO = createChat();
        //TODO - Armazenar perqunta

        Prompt prompt = createPrompt(question);
        Generation generation = callOpenAiClient(prompt);

        return;
    }

    private ChatDTO createChat() {
        try {
            ChatModel chatModel = new ChatModel();
            chatRepository.save(chatModel);
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(chatModel, ChatDTO.class);
        }catch(Exception e) {
            throw new RuntimeException("Falha ao inciar a conversa");
        }
    }

    private Prompt createPrompt(String question) {
        List<Document> results = pgVectorRepository.searchSimilarity(SearchRequest.query(question).withTopK(2));
        PromptTemplate promptTemplate = new PromptTemplate(
                PromptTemplateEnum.DEFAULT.getTemplate(),
                Map.of("information", getDocumentInformationMessage(results), "question", question)
        );
        return promptTemplate.create();
    }

    private Generation callOpenAiClient(Prompt prompt) {
        //TODO - Implementar o chat com o OpenAI e retornar a resposta do chatbot
        return null;
    }

    private String getDocumentInformationMessage(List<Document> results) {
        return results.stream().map(Document::getContent).reduce("", String::concat);
    }
}
