package com.chat_service.message.dto;

import com.chat_service.chatroom.entity.Chatroom;
import com.chat_service.member.entity.Member;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageResponse {
    private String sender;
    private String message;

    @Builder
    public MessageResponse(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }
}
