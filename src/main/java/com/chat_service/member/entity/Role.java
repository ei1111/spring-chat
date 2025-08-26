package com.chat_service.member.entity;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ROLE_USER("사용자"),
    ROLE_CONSULTANT("상담사");

    private final String code;
}
