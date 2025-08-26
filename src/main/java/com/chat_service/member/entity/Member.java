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
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor
@Entity(name = "member")
public class Member {
    @Id
    @Comment("회원 pk")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Comment("이메일")
    @Column(nullable = false, unique = true)
    private String email;

    @Comment("이름")
    private String nickName;

    @Comment("성별")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Comment("전화번호")
    private String phoneNumber;

    @Comment("출생년도")
    private LocalDate birthDate;

    @Comment("직책")
    private String role;

    @Comment("비밀번호")
    private String password;

    @Builder
    public Member(String email, String nickName, Gender gender, String phoneNumber,
            LocalDate birthDate, String role, String password) {
        this.email = email;
        this.nickName = nickName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.role = role;
        this.password = password;
    }

    public Role getRole() {
        return Role.valueOf(role);
    }

    public MemberResponse toResponse() {
        return MemberResponse.builder()
                .memberId(this.memberId)
                .email(this.email)
                .nickName(this.nickName)
                .gender(this.gender)
                .phoneNumber(this.phoneNumber)
                .birthDate(this.birthDate)
                .role(Role.valueOf(role))
                .build();
    }
}
