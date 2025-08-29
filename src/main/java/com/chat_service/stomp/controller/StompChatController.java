package com.chat_service.stomp.controller;

import com.chat_service.chatroom.service.ChatroomService;
import com.chat_service.member.entity.Member;
import com.chat_service.member.service.MemberService;
import com.chat_service.stomp.dto.ChatMessage;
import com.chat_service.stomp.dto.ChatNotification;
import java.security.Principal;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StompChatController {

    private final MemberService memberService;
    private final ChatroomService chatroomService;
    private final SimpMessagingTemplate messagingTemplate;

    // 클라이언트에서 /pub/chats로 발행 하면 여기로 들어온다
    @MessageMapping("/chats/{chatroomId}")
    // /sub/chats으로 메시지 전달
    @SendTo("/sub/chats/{chatroomId}")
    public ChatMessage handleMessage(Principal principal, @DestinationVariable Long chatroomId,
            @Payload Map<String, String> payload) {

        String userId = principal.getName();
        Member member = memberService.findByUserId(userId);

        log.info("{} sent {} in {}", userId, payload, chatroomId);

        chatroomService.saveMessage(member.getMemberId(), chatroomId, payload.get("message"));

        //새로운 메시지 발행
        messagingTemplate.convertAndSend(
                "/sub/chats/news"
                , ChatNotification.of(userId, chatroomId)
        );

        return new ChatMessage(userId, payload.get("message"));
    }
}
