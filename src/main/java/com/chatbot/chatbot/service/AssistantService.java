package com.chatbot.chatbot.service;

import com.chatbot.chatbot.dto.*;
import com.chatbot.chatbot.models.ChatModel;
import com.chatbot.chatbot.models.QuestionModel;
import com.chatbot.chatbot.models.AnswerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AssistantService {

    @Autowired
    private LlmOpenAIService llmOpenAIService;

    @Autowired
    private ChatService chatService;

    public RestResponseDTO sendQuestion(QuestionRecordDTO questionRecordDTO) {
        ChatModel chatModel;
        String answer;

        if(questionRecordDTO.idChat() == null){
            chatModel = chatService.createChat();
            answer = llmOpenAIService.call(questionRecordDTO.question());
        }else{
            chatModel = chatService.findChatById(questionRecordDTO.idChat());
            answer = llmOpenAIService.call(questionRecordDTO.question(), getChatHistory(chatModel));
        }

        AnswerModel answerModel = storeAnswer(chatModel, questionRecordDTO.question(), answer);
        return setResponseDTO(answerModel);
    }

    public void evaluateAnswer(UUID idAnswer, EvaluateAnswerRecordDTO evaluateAnswerRecordDTO) {
        AnswerModel answerModel = chatService.findAnswerById(idAnswer);
        answerModel.setFeedbackStatus(evaluateAnswerRecordDTO.feedbackStatus());
        chatService.updateAnswer(answerModel);
    }

    private AnswerModel storeAnswer(ChatModel chatModel, String question, String answer) {
        QuestionModel questionModel = chatService.createQuestion(chatModel, question);
        return chatService.createAnswer(questionModel, answer);
    }

    private RestResponseDTO setResponseDTO(AnswerModel answerModel) {
        ChatResponseDTO chatResponseDTO = new ChatResponseDTO();

        chatResponseDTO.setIdChat(answerModel.getQuestion().getChat().getIdChat());
        chatResponseDTO.setIdQuestion(answerModel.getQuestion().getIdQuestion());
        chatResponseDTO.setIdAnswer(answerModel.getIdAnswer());
        chatResponseDTO.setAnswer(answerModel.getAnswer());

        return new RestResponseDTO(true, chatResponseDTO);
    }

    private String getChatHistory(ChatModel chatModel) {
        if(chatModel.getQuestions() == null || chatModel.getQuestions().isEmpty()){
            return "";
        }

        List<QuestionModel> lastQuestions = chatModel.getQuestions()
                .stream()
                .sorted(Comparator.comparing(QuestionModel::getDhQuestion).reversed())
                .limit(3)
                .sorted(Comparator.comparing(QuestionModel::getDhQuestion))
                .collect(Collectors.toList());

        return concatQuestions(lastQuestions);
    }

    private String concatQuestions(List<QuestionModel> lastQuestions) {
        StringBuilder chatHistory = new StringBuilder();

        for (QuestionModel questionModel : lastQuestions) {
            String answer = questionModel.getAnswer() == null ? "" : questionModel.getAnswer().getAnswer();
            chatHistory.append("Pergunta: ").append(questionModel.getQuestion())
                    .append(" | Resposta: ").append(answer)
                    .append("\n");
        }

        return chatHistory.toString();
    }
}
