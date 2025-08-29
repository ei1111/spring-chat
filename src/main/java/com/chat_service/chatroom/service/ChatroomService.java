package com.chat_service.chatroom.service;

import com.chat_service.chatroom.dto.ChatroomRequest;
import com.chat_service.chatroom.dto.ChatroomResponse;
import com.chat_service.chatroom.dto.JoinRequest;
import com.chat_service.chatroom.dto.LeaveRequest;
import com.chat_service.chatroom.entity.Chatroom;
import com.chat_service.chatroom.repository.ChatroomRepository;
import com.chat_service.member.entity.Member;
import com.chat_service.member.service.MemberService;
import com.chat_service.memberChatroom.entity.MemberChatroom;
import com.chat_service.memberChatroom.repository.MemberChatroomRepository;
import com.chat_service.memberChatroom.service.MemberChatroomSerivce;
import com.chat_service.message.Repository.MessageRepository;
import com.chat_service.message.dto.MessageResponse;
import com.chat_service.message.entity.Message;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatroomService {

    private final ChatroomRepository chatroomRepository;
    private final MemberService memberService;
    private final MemberChatroomSerivce memberChatroomSerivce;
    private final MessageRepository messageRepository;
    private final MemberChatroomRepository memberChatroomRepository;


    @Transactional
    public ChatroomResponse createChatroom(ChatroomRequest chatroomRequest) {
        Member member = memberService.findById(chatroomRequest.getMemberId());

        Chatroom chatroom = chatroomRequest.toChatroomEntity();

        MemberChatroom memberChatroom = MemberChatroom.of(member, chatroom);
        memberChatroomSerivce.save(memberChatroom);

        //연간관계 매핑
        chatroom.addChatroom(memberChatroom);

        return chatroomRepository.save(chatroom).toResponse();
    }


    //채팅방 참여
    @Transactional
    public boolean joinChartroom(JoinRequest joinRequest) {
        Member member = memberService.findById(joinRequest.getMemberId());
        Chatroom chatroom = findById(joinRequest.getChatroomId());

        if (joinRequest.getCurrentChatroomId() != null) {
            updateLastCheckedAt(member.getMemberId(), joinRequest.getCurrentChatroomId());
        }

        if (memberChatroomSerivce.existsByMemberAndChatroom(member, chatroom)) {
            log.info("이미 참여한 채팅방 입니다.");
            return false;
        }

        memberChatroomSerivce.save(MemberChatroom.of(member, chatroom));
        return true;
    }

    public Chatroom findById(Long id) {
        return chatroomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재 하지 않은 채팅방입니다."));
    }

    //채팅방 떠나기
    @Transactional
    public boolean leaveChatroom(LeaveRequest leaveRequest) {
        Member member = memberService.findById(leaveRequest.getMemberId());
        Chatroom chatroom = findById(leaveRequest.getChatroomId());

        if (!memberChatroomSerivce.existsByMemberAndChatroom(member, chatroom)) {
            log.info("참여하지 않은 방입니다.");
            return false;
        }

        memberChatroomSerivce.leave(member, chatroom);
        return true;
    }

    private void updateLastCheckedAt(Long memberId, Long currentChatroomId) {
        memberChatroomRepository.findByMember_MemberIdAndChatroom_ChatroomId(
                        memberId, currentChatroomId)
                .ifPresent(memberChatroom -> {
                    memberChatroom.updateLastCheckedAt();
                    memberChatroomRepository.save(memberChatroom);
                });
    }

    //등록된 채팅방 조회
    public List<ChatroomResponse> getChatroomList(String userId) {
        return memberChatroomSerivce.findAllByUserId(userId).stream()
                .map(memberChatroom -> {
                    Chatroom chatroom = memberChatroom.getChatroom();
                    chatroom.setHasNewMessages(
                            messageRepository.existsByChatroom_ChatroomIdAndCreatedAtAfter(
                                    chatroom.getChatroomId(),
                                    memberChatroom.getLastCheckedAt()
                            )
                    );
                    return chatroom;
                })
                .map(Chatroom::toResponse)
                .toList();

    }

    //메세지 저장
    @Transactional
    public MessageResponse saveMessage(Long memberId, Long chatroomId, String text) {
        Member member = memberService.findById(memberId);
        Chatroom chatroom = findById(chatroomId);
        Message message = Message.of(text, member, chatroom);
        return messageRepository.save(message).toResponse();
    }

    public List<MessageResponse> getMessageList(Long chatroomId) {
        return messageRepository.findAllByChatroom_ChatroomId(chatroomId).stream()
                .map(Message::toResponse)
                .toList();
    }
}
