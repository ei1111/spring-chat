package com.chat_service.memberChatroom.service;

import com.chat_service.chatroom.entity.Chatroom;
import com.chat_service.member.entity.Member;
import com.chat_service.memberChatroom.entity.MemberChatroom;
import com.chat_service.memberChatroom.repository.MemberChatroomRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberChatroomSerivce {
    private final MemberChatroomRepository memberChatroomRepository;

    @Transactional
    public void save(MemberChatroom memberChatroom) {
        memberChatroomRepository.save(memberChatroom);
    }

    public Boolean existsByMemberAndChatroom(Member member, Chatroom chatroom) {
        return memberChatroomRepository.existsByMemberAndChatroom(member, chatroom);
    }

    @Transactional
    public void leave(Member member,  Chatroom chatroom) {
        memberChatroomRepository.deleteByMemberAndChatroom(member, chatroom);
    }

    public List<MemberChatroom> findAllByUserId(String userId) {
        return memberChatroomRepository.findByMember_UserId(userId);
    }

    public Page<MemberChatroom> findAllByUserIdPage(Member member, Pageable pageable) {
        return memberChatroomRepository.findAllByMember(member, pageable);
    }
}
