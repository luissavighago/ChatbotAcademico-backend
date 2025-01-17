package com.chatbot.chatbot.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_answer")
@Getter
@Setter
public class AnswerModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idAnswer;

    @ManyToOne
    @JoinColumn(name = "idQuestion", nullable = false)
    private QuestionModel question;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String answer;

    //P - POSITIVE, N - NEGATIVE, U - UNRATED
    @Column(nullable = false, length = 1)
    private String feedbackStatus = "U";

    @Column(name = "dhResponse", nullable = false, updatable = false)
    private LocalDateTime dhResponse;

    //Z - ZERO_SHOT, F - FEW_SHOTS, C - CHAIN_OF_THOUGHT, D - DEFAULT
    @Column(nullable = false, length = 1)
    private String promptTechnique = "D";

    @PrePersist
    protected void onCreate() {dhResponse = LocalDateTime.now();}
}
