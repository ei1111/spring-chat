package com.chat_service.chatroom.repository;

import com.chat_service.chatroom.entity.Chatroom;
import com.chat_service.memberChatroom.entity.MemberChatroom;
import java.awt.print.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatroomRepository extends JpaRepository<Chatroom, Long> {

    Page<Chatroom> findAll(Pageable pageable);
}
