package com.chat_service.consultant.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Tag(name = "3. 컨설턴트" , description = "로그인 인증 정보 확인 API")
public class ConsultantController {

    @GetMapping("/")
    public String home() {
        return "redirect:/login.html";
    }

    @ResponseBody
    @GetMapping("/consultants/me")
    @Operation(summary = "회원의 인증 정보 API")
    public ResponseEntity<Map<String, String>> getCurrentUser(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Not logged in"));
        }

        Map<String, String> result = new HashMap<>();
        result.put("userId", principal.getName());
        return ResponseEntity.ok(result);
    }
}
