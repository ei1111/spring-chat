package com.chat_service.member.dto;

import com.chat_service.member.entity.Gender;
import com.chat_service.member.entity.Role;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberResponse {

    private Long memberId;

    private String userId;

    private String email;

    private String name;

    private Gender gender;

    private String phoneNumber;

    private LocalDate birthDate;

    private Role role;

    @Builder

    public MemberResponse(Long memberId, String userId, String email, String name, Gender gender,
            String phoneNumber, LocalDate birthDate, Role role) {
        this.memberId = memberId;
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.role = role;
    }
}
