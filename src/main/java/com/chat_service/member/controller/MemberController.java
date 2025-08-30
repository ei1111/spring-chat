package com.chat_service.member.controller;

import com.chat_service.member.dto.MemberRequest;
import com.chat_service.member.dto.MemberResponse;
import com.chat_service.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "1. 회원가입" , description = "회원 가입 API")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/members")
    @Operation(summary = "회원의 정보를 저장할 수 있다.")
    public ResponseEntity<MemberResponse> save(@RequestBody MemberRequest memberRequest) {
        return new ResponseEntity(memberService.save(memberRequest), HttpStatus.CREATED);
    }
}
