package com.chat_service.chatroom.repository;

import com.chat_service.chatroom.entity.Chatroom;
import com.chat_service.member.entity.Member;
import com.chat_service.memberChatroom.entity.MemberChatroom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatroomRepository extends JpaRepository<Chatroom, Long> {
    @EntityGraph(attributePaths = "memberChatrooms") // memberChatrooms도 같이 조회
    Page<Chatroom> findAll(Pageable pageable);
}
