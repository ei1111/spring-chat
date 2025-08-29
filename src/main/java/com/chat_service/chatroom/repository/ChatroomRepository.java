package com.chat_service.chatroom.repository;

import com.chat_service.chatroom.entity.Chatroom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatroomRepository extends JpaRepository<Chatroom, Long> {

    Page<Chatroom> findAll(Pageable pageable);
}
