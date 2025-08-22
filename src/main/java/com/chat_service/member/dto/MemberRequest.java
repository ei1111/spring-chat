package com.chat_service.member.dto;

import com.chat_service.member.entity.Gender;
import com.chat_service.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberRequest {
    private String email;

    private String nickName;

    private Gender gender;

    private String phoneNumber;

    private LocalDate birthDate;

    public Member toMemberEntity() {
        return Member.builder()
                .email(this.email)
                .nickName(this.nickName)
                .gender(this.gender)
                .phoneNumber(this.phoneNumber)
                .birthDate(this.birthDate)
                .build();
    }
}
