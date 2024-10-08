package com.chatbot.chatbot.service;

import com.chatbot.chatbot.models.ChatModel;
import com.chatbot.chatbot.models.QuestionModel;
import com.chatbot.chatbot.models.AnswerModel;
import com.chatbot.chatbot.repository.ChatRepository;
import com.chatbot.chatbot.repository.QuestionRepository;
import com.chatbot.chatbot.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

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

    public AnswerModel createAnswer(QuestionModel questionModel, String response) {
        try{
            AnswerModel answerModel = new AnswerModel();
            answerModel.setAnswer(response);
            answerModel.setQuestion(questionModel);

            answerRepository.save(answerModel);

            return answerModel;
        }catch (Exception e) {
            throw new RuntimeException("Falha ao armazenar a resposta");
        }
    }

    public ChatModel findChatById(UUID id) {
        return chatRepository.findById(id).orElseThrow(() -> new RuntimeException("Chat não encontrado"));
    }
}
