package com.chatbot.chatbot.service;

import com.chatbot.chatbot.dto.AnswerResponseDTO;
import com.chatbot.chatbot.dto.PromptResponseDTO;
import com.chatbot.chatbot.dto.QuestionRecordDTO;
import com.chatbot.chatbot.dto.RestResponseDTO;
import com.chatbot.chatbot.enums.PromptTechniqueEnum;
import com.chatbot.chatbot.models.AnswerModel;
import com.chatbot.chatbot.models.ChatModel;
import com.chatbot.chatbot.models.QuestionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PromptsService {

    @Autowired
    private LlmOpenAIService llmOpenAIService;

    @Autowired
    private ChatService chatService;

    public RestResponseDTO askWithPrompts(QuestionRecordDTO questionRecordDTO) {
        ChatModel chatModel = chatService.createChat();
        QuestionModel questionModel = chatService.createQuestion(chatModel, questionRecordDTO.question());
        List<AnswerModel> answerModelList = new ArrayList<>();

        for (PromptTechniqueEnum technique : PromptTechniqueEnum.values()) {
            if(technique.equals(PromptTechniqueEnum.DEFAULT)) {
                continue;
            }

            String answer = llmOpenAIService.callUsingPromptTechnique(questionRecordDTO, technique);
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
}
