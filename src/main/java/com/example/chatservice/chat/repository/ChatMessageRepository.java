package com.example.chatservice.chat.repository;

import com.example.chatservice.chat.ChatMessage;
import com.example.chatservice.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatRoom(ChatRoom chatRoom);
    // 메시지를 여러개 불러와야하므로 List로 함.
}
