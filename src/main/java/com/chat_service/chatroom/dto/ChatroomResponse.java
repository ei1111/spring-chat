package com.chat_service.chatroom.dto;

import com.chat_service.memberChatroom.dto.MemberChatroomResponse;
import com.chat_service.memberChatroom.entity.MemberChatroom;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@NoArgsConstructor
public class ChatroomResponse {
    private Long id;
    private String title;
    private Integer chatCount;
    private Boolean hasNewMessages;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Builder
    public ChatroomResponse(Long id, String title, Integer chatCount, Boolean hasNewMessages,
            LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.chatCount = chatCount;
        this.hasNewMessages = hasNewMessages;
        this.createdAt = createdAt;
    }
}
