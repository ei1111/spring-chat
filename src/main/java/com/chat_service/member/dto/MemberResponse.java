package com.chat_service.member.dto;

import com.chat_service.member.entity.Gender;
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

    private String email;

    private String nickName;

    private Gender gender;

    private String phoneNumber;

    private LocalDate birthDate;

    @Builder
    public MemberResponse(Long memberId, String email, String nickName, Gender gender,
            String phoneNumber, LocalDate birthDate) {
        this.memberId = memberId;
        this.email = email;
        this.nickName = nickName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
    }
}
