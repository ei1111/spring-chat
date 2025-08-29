package com.chat_service.member.dto;

import com.chat_service.member.entity.Gender;
import com.chat_service.member.entity.Member;
import com.chat_service.member.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@NoArgsConstructor
public class MemberRequest {

    @Schema(name = "userId", description = "회원 아이디", example = "consultant")
    private String userId;

    @Schema(name = "password", description = "비밀번호", example = "1")
    private String password;

    @Schema(name = "name", description = "이름", example = "사용자")
    private String name;

    @Schema(name = "email", description = "이메일", example = "abc@naver.com")
    private String email;

    @Schema(name = "gender", description = "성별", example = "MALE")
    private Gender gender;

    @Schema(name = "phoneNumber", description = "전화번호", example = "010-1111-2222")
    private String phoneNumber;

    @Schema(name = "birthDate", description = "생년월일", example = "2025-08-29")
    private LocalDate birthDate;


    public Member toMemberEntity() {
        return Member.builder()
                .userId(this.userId)
                .email(this.email)
                .name(this.name)
                .gender(this.gender)
                .role(determineRole())
                .phoneNumber(this.phoneNumber)
                .birthDate(this.birthDate)
                .password(this.password)
                .build();
    }

    public void passwordEncoding(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    private String determineRole() {
        return this.userId.equalsIgnoreCase("consultant") ?
                Role.ROLE_CONSULTANT.toString() : Role.ROLE_USER.toString();
    }
}
