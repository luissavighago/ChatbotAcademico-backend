package com.chatbot.chatbot.service;

import com.chatbot.chatbot.dto.ChatDTO;
import com.chatbot.chatbot.models.ChatModel;
import com.chatbot.chatbot.models.QuestionModel;
import com.chatbot.chatbot.models.ResponseModel;
import com.chatbot.chatbot.repository.ChatRepository;
import com.chatbot.chatbot.repository.QuestionRepository;
import com.chatbot.chatbot.repository.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ResponseRepository responseRepository;

    public ChatModel createChat() {
        try {
            ChatModel chatModel = new ChatModel();
            chatRepository.save(chatModel);
            return chatModel;
        }catch(Exception e) {
            throw new RuntimeException("Falha ao inciar a conversa");
        }
    }

    public QuestionModel createQuestion(ChatModel chatModel, String question) {
        try {
            QuestionModel questionModel = new QuestionModel();
            questionModel.setQuestion(question);
            questionModel.setChat(chatModel);

            questionRepository.save(questionModel);

            return questionModel;
        }catch(Exception e) {
            throw new RuntimeException("Falha criar a pergunta");
        }
    }

    public ResponseModel createResponse(QuestionModel questionModel, String response) {
        try{
            ResponseModel responseModel = new ResponseModel();
            responseModel.setResponse(response);
            responseModel.setQuestion(questionModel);

            responseRepository.save(responseModel);

            return responseModel;
        }catch (Exception e) {
            throw new RuntimeException("Falha ao armazenar a resposta");
        }
    }
}
