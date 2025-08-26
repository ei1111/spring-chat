package com.chat_service.memberChatroom.entity;

import com.chat_service.chatroom.entity.Chatroom;
import com.chat_service.member.dto.MemberResponse;
import com.chat_service.member.entity.Member;
import com.chat_service.memberChatroom.dto.MemberChatroomResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor
@Entity(name = "member_chatroom")
public class MemberChatroom {
    @Id
    @Comment("pk")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberChatroomId;

    @Comment("member fk")
    @JoinColumn(name = "memberId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Comment("chatroom fk")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroomId")
    private Chatroom chatroom;

    private LocalDateTime lastCheckedAt;

    public void updateLastCheckedAt() {
        this.lastCheckedAt = LocalDateTime.now();
    }

    @Builder
    public MemberChatroom(Member member, Chatroom chatroom) {
        this.member = member;
        this.chatroom = chatroom;
    }

    public static MemberChatroom of(Member member, Chatroom chatroom) {
        return new MemberChatroom(member, chatroom);
    }

    public MemberChatroomResponse toResponse() {
        return MemberChatroomResponse.builder()
                .memberResponse(member.toResponse())
                .build();
    }
}
