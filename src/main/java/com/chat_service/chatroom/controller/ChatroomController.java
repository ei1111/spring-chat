package com.chat_service.chatroom.controller;

import com.chat_service.chatroom.dto.ChatroomRequest;
import com.chat_service.chatroom.dto.ChatroomResponse;
import com.chat_service.chatroom.dto.JoinRequest;
import com.chat_service.chatroom.dto.LeaveRequest;
import com.chat_service.chatroom.entity.Chatroom;
import com.chat_service.chatroom.service.ChatroomService;
import com.chat_service.member.service.MemberService;
import com.chat_service.message.dto.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "2. 채팅" , description = "채팅 API")
public class ChatroomController {
    private final ChatroomService chatroomService;

    @PostMapping
    @Operation(summary = "채팅방을 만들 수 있다.")
    public ResponseEntity<ChatroomResponse> createChatroom(@RequestBody ChatroomRequest request) {
        return new ResponseEntity(chatroomService.createChatroom(request), HttpStatus.CREATED);
    }

    @PostMapping("/chatrooms")
    @Operation(summary = "채팅방에 들어갈 수 있다.")
    public boolean joinChatroom(@RequestBody JoinRequest request) {
        return chatroomService.joinChartroom(request);
    }

    @DeleteMapping
    @Operation(summary = "채팅방을 떠날 수 있다.")
    public void leaveChatroom(@RequestBody LeaveRequest request) {
        chatroomService.leaveChatroom(request);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "채팅방을 가져올 수 있다.\n "
            + "컨설턴트는 모든 채팅방을 볼 수 있다\n"
            + "일반 유저는 자신의 채팅방만 볼 수 있다.")
    public ResponseEntity<Page<ChatroomResponse>> findChatrooms(@PathVariable String userId, Pageable pageable) {
        // 무조건 size=10 으로 제한
        Pageable fixedPageable = PageRequest.of(pageable.getPageNumber(), 10, pageable.getSort());

        return ResponseEntity.ok().body(chatroomService.getChatroomPage(userId, fixedPageable));
    }


    @GetMapping("/{chatroomId}/messages")
    @Operation(summary = "채팅방의 메세지를 가져올 수 있다.")
    public ResponseEntity<List<MessageResponse>> getMessageList(@PathVariable Long chatroomId) {
        return ResponseEntity.ok().body(chatroomService.getMessageList(chatroomId));
    }
}
