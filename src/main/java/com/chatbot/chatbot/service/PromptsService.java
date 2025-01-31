package com.chatbot.chatbot.service;

import com.chatbot.chatbot.dto.AnswerResponseDTO;
import com.chatbot.chatbot.dto.PromptResponseDTO;
import com.chatbot.chatbot.dto.QuestionRecordDTO;
import com.chatbot.chatbot.dto.RestResponseDTO;
import com.chatbot.chatbot.enums.PromptTechniqueEnum;
import com.chatbot.chatbot.models.AnswerModel;
import com.chatbot.chatbot.models.ChatModel;
import com.chatbot.chatbot.models.QuestionModel;
import com.chatbot.chatbot.repository.PGVectorRepository;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PromptsService {

    @Autowired
    private LlmOpenAIService llmOpenAIService;

    @Autowired
    private PGVectorRepository pgVectorRepository;

    @Autowired
    private ChatService chatService;

    public RestResponseDTO askWithPrompts(QuestionRecordDTO questionRecordDTO) {
        ChatModel chatModel = chatService.createChat();
        QuestionModel questionModel = chatService.createQuestion(chatModel, questionRecordDTO.question());
        List<AnswerModel> answerModelList = new ArrayList<>();
        String context = getContext(questionRecordDTO.question());

        for (PromptTechniqueEnum technique : PromptTechniqueEnum.values()) {
            if(technique.equals(PromptTechniqueEnum.DEFAULT)) {
                continue;
            }

            String answer = llmOpenAIService.callUsingPromptTechnique(questionRecordDTO, technique, context);
            AnswerModel answerModel = chatService.createAnswer(questionModel, answer, technique);
            answerModelList.add(answerModel);
        }

        return setResponseDTO(answerModelList);
    }

    private RestResponseDTO setResponseDTO(List<AnswerModel> answerModelList) {
        PromptResponseDTO promptResponseDTO = new PromptResponseDTO();
        List<AnswerResponseDTO> answerResponseDTOList = new ArrayList<>();

        promptResponseDTO.setIdChat(answerModelList.get(0).getQuestion().getChat().getIdChat());
        promptResponseDTO.setIdQuestion(answerModelList.get(0).getQuestion().getIdQuestion());

        for (AnswerModel answerModel : answerModelList) {
            AnswerResponseDTO answerResponseDTO = new AnswerResponseDTO();

            answerResponseDTO.setIdAnswer(answerModel.getIdAnswer());
            answerResponseDTO.setAnswer(answerModel.getAnswer());
            answerResponseDTO.setPromptTechnique(answerModel.getPromptTechnique());

            answerResponseDTOList.add(answerResponseDTO);
        }

        promptResponseDTO.setAnswers(answerResponseDTOList);
        return new RestResponseDTO(true, promptResponseDTO);
    }

    private String getContext(String question) {
        List<Document> results = pgVectorRepository.searchSimilarity(
                SearchRequest.defaults()
                        .withQuery(question)
                        .withTopK(8)
                        .withSimilarityThreshold(0.7)
        );

        return results.stream().map(Document::getContent).reduce("", String::concat);
    }
}
