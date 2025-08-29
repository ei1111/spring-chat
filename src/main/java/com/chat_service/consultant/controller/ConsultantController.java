package com.chat_service.consultant.controller;

import com.chat_service.chatroom.dto.ChatroomResponse;
import com.chat_service.consultant.service.ConsultantService;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/consultants")
public class ConsultantController {

    private final ConsultantService consultantService;

/*    @GetMapping
    public String index() {
        return "consultants/index.html";
    }*/
    @ResponseBody
    @GetMapping("/me")
    public ResponseEntity<Map<String, String>> getCurrentUser(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Not logged in"));
        }

        Map<String, String> result = new HashMap<>();
        result.put("userId", principal.getName());
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public String index() {
        return "index.html";
    }

    @ResponseBody
    @GetMapping("/chats")
    public Page<ChatroomResponse> getChatrooms(@RequestParam Pageable pageable) {
        return consultantService.getChatroomPage(pageable);
    }
}
