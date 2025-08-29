package com.chat_service.message.entity;

import com.chat_service.chatroom.entity.Chatroom;
import com.chat_service.member.entity.Member;
import com.chat_service.message.dto.MessageResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroomId")
    private Chatroom chatroom;

    private LocalDateTime createdAt;

    private Message(String text, Member member, Chatroom chatroom) {
        this.text = text;
        this.member = member;
        this.chatroom = chatroom;
        this.createdAt = LocalDateTime.now();
    }

    public static Message of(String text, Member member, Chatroom chatroom) {
        return new Message(text, member, chatroom);
    }

    public MessageResponse toResponse() {
        return MessageResponse.builder()
                .sender(member.getName())
                .message(text)
                .build();
    }
}
