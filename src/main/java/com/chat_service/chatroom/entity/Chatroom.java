package com.chat_service.chatroom.entity;

import com.chat_service.chatroom.dto.ChatroomResponse;
import com.chat_service.memberChatroom.entity.MemberChatroom;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor
@Entity(name = "chatroom")
public class Chatroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatroomId;

    @Comment("채팅방 이름")
    private String title;

    @Comment("생성날짜")
    private LocalDateTime createdAt;

    @Setter
    @Transient
    private Boolean hasNewMessages;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chatroom")
    private List<MemberChatroom> memberChatrooms = new ArrayList<>();

    public void addChatroom(MemberChatroom memberChatroom) {
        memberChatrooms.add(memberChatroom);
    }

    @Builder
    public Chatroom(String title, LocalDateTime createdAt) {
        this.title = title;
        this.createdAt = createdAt;
    }

    public ChatroomResponse toResponse() {
        return ChatroomResponse.builder()
                .id(this.chatroomId)
                .title(this.title)
                .createdAt(this.createdAt)
                .memberCount(memberChatrooms.size())
                .hasNewMessages(hasNewMessages)
                .build();
    }
}
