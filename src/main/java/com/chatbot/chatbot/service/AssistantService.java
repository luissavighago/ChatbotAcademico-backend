package com.chatbot.chatbot.service;

import com.chatbot.chatbot.dto.ChatResponseDTO;
import com.chatbot.chatbot.dto.RestResponseDTO;
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

    public RestResponseDTO sendQuestion(String question) {
        String answer = llmOpenAIService.call(question);

        //armazenar os dados da conversa
        ChatModel chatModel = chatService.createChat();
        QuestionModel questionModel = chatService.createQuestion(chatModel, question);
        AnswerModel answerModel = chatService.createAnswer(questionModel, answer);

        return setResponseDTO(answerModel);
    }

    public RestResponseDTO sendQuestion(String question, UUID idChat) {
        ChatModel chatModel = chatService.findChatById(idChat);
        String chatHistory = getChatHistory(chatModel);

        String answer = llmOpenAIService.call(question, chatHistory);

        //armazenar os dados da conversa
        QuestionModel questionModel = chatService.createQuestion(chatModel, question);
        AnswerModel answerModel = chatService.createAnswer(questionModel, answer);

        return setResponseDTO(answerModel);
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
        StringBuilder jsonResult = new StringBuilder("[");

        for (QuestionModel questionModel : lastQuestions) {
            String answer = questionModel.getAnswer() == null ? "" : questionModel.getAnswer().getAnswer();
            jsonResult.append("{")
                    .append("\"question\":\"").append(questionModel.getQuestion()).append("\",")
                    .append("\"response\":\"").append(answer).append("\"")
                    .append("},");
        }

        // Remove a última vírgula e fecha o JSON
        if (jsonResult.length() > 1) {
            jsonResult.deleteCharAt(jsonResult.length() - 1);
        }
        jsonResult.append("]");

        return jsonResult.toString();
    }

    private String formatQuestionAnswer(QuestionModel questionModel) {
        String answer = questionModel.getAnswer() == null ? "" : questionModel.getAnswer().getAnswer();
        return String.format("{\"question\":\"%s\", \"response\":\"%s\"}", questionModel.getQuestion(), answer);
    }
}
