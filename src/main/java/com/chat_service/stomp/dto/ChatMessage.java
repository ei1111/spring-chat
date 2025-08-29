package com.chat_service.stomp.dto;

public record ChatMessage(
        String userId,
        String message
) {

}
