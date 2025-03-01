package com.dalona.waa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatMessage {

    private String user;
    private String assistant;

    public ChatMessage(String user, String assistant) {
        this.user = user;
        this.assistant = assistant;
    }
}
