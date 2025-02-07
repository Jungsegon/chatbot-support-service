package com.example.chatservice.chat.repository;

import com.example.chatservice.chat.Entity.ChatRoom;
import com.example.chatservice.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByRoomId(String roomId);

    Optional<ChatRoom> findByUserAndAdmin(Member user, Member admin);

    Optional<ChatRoom> findByUser(Member user);
}
