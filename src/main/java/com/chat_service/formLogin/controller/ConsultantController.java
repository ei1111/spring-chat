package com.chat_service.formLogin.controller;

import com.chat_service.chatroom.dto.ChatroomResponse;
import com.chat_service.formLogin.service.ConsultantService;
import com.chat_service.member.dto.MemberRequest;
import com.chat_service.member.dto.MemberResponse;
import java.awt.print.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/consultants")
public class ConsultantController {

    private final ConsultantService consultantService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<MemberResponse> save(@RequestBody MemberRequest memberRequest) {
        return new ResponseEntity(consultantService.save(memberRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public String index() {
        return "consultants/index.html";
    }

    @GetMapping("/chats")
    public Page<ChatroomResponse> getChatrooms(@RequestParam  Pageable pageable) {
        return consultantService.getChatroomPage(pageable);
    }
}
