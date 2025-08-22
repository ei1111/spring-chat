package com.chat_service.stomp.dto;

public record ChatMessage(
        String sender,
        String message
) {

}
