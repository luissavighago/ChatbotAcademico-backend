package com.chatbot.chatbot.enums;

public enum PromptTechniqueEnum {
    ZERO_SHOT("Z"),
    FEW_SHOTS("F"),
    CHAIN_OF_THOUGHT("C"),
    DEFAULT("D");

    private final String key;

    PromptTechniqueEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
