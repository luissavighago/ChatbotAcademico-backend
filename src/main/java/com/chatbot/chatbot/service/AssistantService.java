package com.chatbot.chatbot.service;

import com.chatbot.chatbot.dto.ChatResponseDTO;
import com.chatbot.chatbot.dto.RestResponseDTO;
import com.chatbot.chatbot.enums.PromptTemplateEnum;
import com.chatbot.chatbot.models.ChatModel;
import com.chatbot.chatbot.models.QuestionModel;
import com.chatbot.chatbot.models.AnswerModel;
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
public class AssistantService {

    private final OpenAiChatModel chatModel;

    @Autowired
    private ChatService chatService;

    @Autowired
    private PGVectorRepository pgVectorRepository;

    public AssistantService(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public RestResponseDTO sendQuestion(String question) {
        if (question == null || question.isBlank()) {
            //TODO - Melhorar tratativa de exceção
            throw new RuntimeException("Pergunta é obrigatória");
        }

        ChatResponse chatResponse = callOpenAiChatModel(createPrompt(question));
        String response = chatResponse.getResult().getOutput().getContent();
        if(response == null  || response.isBlank()) {
            throw new RuntimeException("Falha ao obter a resposta da API da OpenAI");
        }

        //armazenar os dados da conversa
        ChatModel chatModel = chatService.createChat();
        QuestionModel questionModel = chatService.createQuestion(chatModel, question);
        AnswerModel answerModel = chatService.createAnswer(questionModel, response);

        return setResponseDTO(answerModel);
    }

    private RestResponseDTO setResponseDTO(AnswerModel answerModel) {
        //TODO - Adicionar validação dos dados

        ChatResponseDTO chatResponseDTO = new ChatResponseDTO();

        chatResponseDTO.setIdChat(answerModel.getQuestion().getChat().getIdChat());
        chatResponseDTO.setIdQuestion(answerModel.getQuestion().getIdQuestion());
        chatResponseDTO.setIdAnswer(answerModel.getIdAnswer());
        chatResponseDTO.setAnswer(answerModel.getAnswer());

        return new RestResponseDTO(true, chatResponseDTO);
    }

    private Prompt createPrompt(String question) {
        List<Document> results = pgVectorRepository.searchSimilarity(SearchRequest.query(question).withTopK(2));
        PromptTemplate promptTemplate = new PromptTemplate(
                PromptTemplateEnum.DEFAULT.getTemplate(),
                Map.of("information", getDocumentInformationMessage(results), "question", question)
        );
        return promptTemplate.create();
    }

    private ChatResponse callOpenAiChatModel(Prompt prompt) {
        try {
            return chatModel.call(prompt);
        }catch (Exception e) {
            throw new RuntimeException("Falha ao acessar a API da OpenAI");
        }
    }

    private String getDocumentInformationMessage(List<Document> results) {
        return results.stream().map(Document::getContent).reduce("", String::concat);
    }
}
