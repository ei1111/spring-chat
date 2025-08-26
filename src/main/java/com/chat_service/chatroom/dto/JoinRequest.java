package com.chat_service.chatroom.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JoinRequest {
    private Long memberId;
    private Long chatroomId;
    private Long currentChatroomId;
}
