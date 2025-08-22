package com.chat_service.member.entity;

import com.chat_service.member.dto.MemberResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false, unique = true)
    private String email;

    private String nickName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String phoneNumber;

    private LocalDate birthDate;

    @Builder
    public Member(String email, String nickName, Gender gender, String phoneNumber,
            LocalDate birthDate) {
        this.email = email;
        this.nickName = nickName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
    }

    public MemberResponse toResponse() {
        return MemberResponse.builder()
                .memberId(this.memberId)
                .email(this.email)
                .nickName(this.nickName)
                .gender(this.gender)
                .phoneNumber(this.phoneNumber)
                .birthDate(this.birthDate)
                .build();
    }
}
