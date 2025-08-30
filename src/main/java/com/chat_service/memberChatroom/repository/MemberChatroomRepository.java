package com.chat_service.memberChatroom.repository;

import com.chat_service.chatroom.entity.Chatroom;
import com.chat_service.member.entity.Member;
import com.chat_service.memberChatroom.entity.MemberChatroom;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberChatroomRepository extends JpaRepository<MemberChatroom, Long> {
    Boolean existsByMemberAndChatroom(Member member, Chatroom chatroom);

    void deleteByMember_MemberIdAndChatroom_ChatroomId(Long memberId, Long chatroomId);

    List<MemberChatroom> findByMember_UserId(String userId);

    Optional<MemberChatroom> findByMember_MemberIdAndChatroom_ChatroomId(Long memberId, Long currentChatroomId);

    Page<MemberChatroom> findAllByMember(Member member, Pageable pageable);
}
