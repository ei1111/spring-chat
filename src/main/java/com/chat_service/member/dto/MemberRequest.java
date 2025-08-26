package com.chat_service.member.dto;

import com.chat_service.member.entity.Gender;
import com.chat_service.member.entity.Member;
import com.chat_service.member.entity.Role;
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
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@NoArgsConstructor
public class MemberRequest {
    private String email;

    private String password;

    private String nickName;

    private Gender gender;

    private Role role;

    private String phoneNumber;

    private LocalDate birthDate;


    public Member toMemberEntity() {
        return Member.builder()
                .email(this.email)
                .nickName(this.nickName)
                .gender(this.gender)
                .role(role.toString())
                .phoneNumber(this.phoneNumber)
                .birthDate(this.birthDate)
                .password(this.password)
                .build();
    }

    public void passwordEncoding(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }
}
