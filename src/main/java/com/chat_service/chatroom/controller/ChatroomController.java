package com.chat_service.chatroom.controller;

import com.chat_service.chatroom.dto.ChatroomRequest;
import com.chat_service.chatroom.dto.ChatroomResponse;
import com.chat_service.chatroom.dto.JoinRequest;
import com.chat_service.chatroom.dto.LeaveRequest;
import com.chat_service.chatroom.entity.Chatroom;
import com.chat_service.chatroom.service.ChatroomService;
import com.chat_service.member.service.MemberService;
import com.chat_service.message.dto.MessageResponse;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chats")
public class ChatroomController {
    private final ChatroomService chatroomService;

    @PostMapping
    public ResponseEntity<ChatroomResponse> createChatroom(@RequestBody ChatroomRequest request) {
        return new ResponseEntity(chatroomService.createChatroom(request), HttpStatus.CREATED);
    }

    @PostMapping("/chatrooms")
    public boolean joinChatroom(@RequestBody JoinRequest request) {
        return chatroomService.joinChartroom(request);
    }

    @DeleteMapping
    public void leaveChatroom(@RequestBody LeaveRequest request) {
        chatroomService.leaveChatroom(request);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Page<ChatroomResponse>> findChatrooms(@PathVariable String userId, Pageable pageable) {
        // 무조건 size=10 으로 제한
        Pageable fixedPageable = PageRequest.of(pageable.getPageNumber(), 10, pageable.getSort());

        return ResponseEntity.ok().body(chatroomService.getChatroomPage(userId, fixedPageable));
    }



    @GetMapping("/{chatroomId}/messages")
    public ResponseEntity<List<MessageResponse>> getMessageList(@PathVariable Long chatroomId) {
        return ResponseEntity.ok().body(chatroomService.getMessageList(chatroomId));
    }
}
