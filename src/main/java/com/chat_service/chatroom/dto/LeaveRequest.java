package com.chat_service.chatroom.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LeaveRequest {
    private String userId;
    private Long chatroomId;

}
