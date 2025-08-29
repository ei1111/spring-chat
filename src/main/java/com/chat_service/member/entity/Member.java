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

    @Comment("아이디")
    @Column(nullable = false, unique = true)
    private String userId;

    @Comment("비밀번호")
    private String password;

    @Comment("이메일")
    @Column(nullable = false, unique = true)
    private String email;

    @Comment("이름")
    private String name;

    @Comment("성별")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Comment("전화번호")
    private String phoneNumber;

    @Comment("출생년도")
    private LocalDate birthDate;

    @Comment("직책")
    private String role;

    @Builder
    public Member(String userId, String password, String email, String name, Gender gender,
            String phoneNumber, LocalDate birthDate, String role) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.role = role;
    }

    public Role getRole() {
        return Role.valueOf(role);
    }

    public MemberResponse toResponse() {
        return MemberResponse.builder()
                .memberId(this.memberId)
                .userId(this.userId)
                .name(this.name)
                .email(this.email)
                .gender(this.gender)
                .phoneNumber(this.phoneNumber)
                .birthDate(this.birthDate)
                .role(Role.valueOf(role))
                .build();
    }
}
