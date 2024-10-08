package com.chatbot.chatbot.enums;

public enum PromptTemplateEnum {
    DEFAULT("""
        Você responde perguntas sobre o regulamento de estágios de uma universidade.
        Use o conteúdo do regulamento abaixo para responder as perguntas do usuário.
        Se a resposta não for encontrada no regulamento, responda que você não sabe, não tente inventar uma resposta.
        Regulamento:
        {information}
        
        Pergunta:
        {question}
    """),

    CHAT_HISTORY("""
        Você responde perguntas sobre o regulamento de estágios de uma universidade.
        Use o conteúdo do regulamento abaixo para responder as perguntas do usuário.
        Se a resposta não for encontrada no regulamento, responda que você não sabe, não tente inventar uma resposta.
        Em formato JSON segue o histórico com as mensagens mais recentes da conversa.
        Continue a conversa de maneira coerente, respondendo a pergunta do usuário com base no contexto fornecido:
        
        Historico da conversa:
        {chat_history}
        
        Regulamento:
        {information}
        
        Pergunta:
        {question}
    """);

    private final String template;

    PromptTemplateEnum(String template) {
        this.template = template;
    }

    public String getTemplate() {
        return template;
    }
}
