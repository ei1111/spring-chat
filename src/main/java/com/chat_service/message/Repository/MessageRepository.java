package com.chat_service.message.Repository;

import com.chat_service.message.dto.MessageResponse;
import com.chat_service.message.entity.Message;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByChatroom_ChatroomId(Long chatroomId);

    Boolean existsByChatroom_ChatroomIdAndCreatedAtAfter(Long chatroomId, LocalDateTime lastCheckedAt);
}
