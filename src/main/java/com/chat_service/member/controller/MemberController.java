package com.chat_service.member.controller;

import com.chat_service.member.dto.MemberRequest;
import com.chat_service.member.dto.MemberResponse;
import com.chat_service.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity<MemberResponse> save(@RequestBody MemberRequest memberRequest) {
        return new ResponseEntity(memberService.save(memberRequest), HttpStatus.CREATED);
    }
}
