package com.chat_service.chatroom.dto;

import com.chat_service.chatroom.entity.Chatroom;
import com.chat_service.memberChatroom.entity.MemberChatroom;
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
public class ChatroomRequest {
    private Long memberId;
    private String title;

    public Chatroom toChatroomEntity() {
        return Chatroom.builder()
                .title(this.title)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
