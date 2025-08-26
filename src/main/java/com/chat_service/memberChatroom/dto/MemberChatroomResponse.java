package com.chat_service.memberChatroom.dto;

import com.chat_service.member.dto.MemberResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberChatroomResponse {
    private MemberResponse memberResponse;


    @Builder
    public MemberChatroomResponse(MemberResponse memberResponse) {
        this.memberResponse = memberResponse;
    }
}
