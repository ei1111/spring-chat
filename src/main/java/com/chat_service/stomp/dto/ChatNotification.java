package com.chat_service.stomp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatNotification {
    private String userId;
    private Long chatroomId;

    @Builder
    public ChatNotification(String userId, Long chatroomId) {
        this.userId = userId;
        this.chatroomId = chatroomId;
    }

    public static ChatNotification of(String userId, Long chatroomId) {
        return ChatNotification.builder()
                .chatroomId(chatroomId)
                .userId(userId)
                .build();
    }
}
