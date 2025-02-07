package com.example.chatservice.chat.service;

import com.example.chatservice.chat.Entity.ChatMessage;
import com.example.chatservice.chat.Entity.ChatRoom;
import com.example.chatservice.chat.repository.ChatMessageRepository;
import com.example.chatservice.chat.repository.ChatRoomRepository;
import com.example.chatservice.member.Member;
import com.example.chatservice.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;

    // ✅ 사용자와 관리자 간 1:1 채팅방을 생성하거나 기존 채팅방을 반환
    public ChatRoom createOrFindRoom(String userAccount) {
        Member user = memberRepository.findByAccount(userAccount)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        Member admin = memberRepository.findByAccount("admin")
                .orElseThrow(() -> new EntityNotFoundException("관리자를 찾을 수 없습니다."));

        Optional<ChatRoom> existingRoom = chatRoomRepository.findByUser(user);
        if (existingRoom.isPresent()) {
            log.info("✅ 기존 채팅방이 존재함: roomId = {}", existingRoom.get().getRoomId());
            return existingRoom.get();
        } else {
            log.info("❌ 기존 채팅방이 없음, 새로운 채팅방을 생성함: user = {}", userAccount);
        }

        return existingRoom.orElseGet(() -> chatRoomRepository.save(
                ChatRoom.builder()
                        .roomId(UUID.randomUUID().toString())
                        .name(user.getName() + " - 상담")
                        .user(user)
                        .admin(admin)
                        .build()
        ));
    }

    // ✅ 채팅 메시지를 저장
    public ChatMessage saveMessage(String roomId, String sender, String content) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId)
                .orElseGet(() -> createChatRoom(roomId)); // ✅ 채팅방이 없으면 자동 생성

        // ✅ recipient 설정: sender가 admin이면 roomId(사용자 account), sender가 사용자면 recipient=admin
        String recipient = "admin".equals(sender) ? roomId : "admin";

        ChatMessage message = ChatMessage.builder()
                .sender(sender)
                .recipient(recipient)
                .content(content)
                .chatRoom(chatRoom)
                .build();

        return chatMessageRepository.save(message);
    }

    // ✅ roomId=userAccount인 새로운 채팅방 생성
    private ChatRoom createChatRoom(String userAccount) {
        Member user = memberRepository.findByAccount(userAccount)
                .orElseThrow(() -> new EntityNotFoundException("❌ 유저를 찾을 수 없습니다: " + userAccount));

        Member admin = memberRepository.findByAccount("admin")
                .orElseThrow(() -> new EntityNotFoundException("❌ 관리자 계정을 찾을 수 없습니다."));

        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(userAccount) // ✅ userAccount를 roomId로 사용
                .name(userAccount + " - 상담 채팅방")
                .user(user)
                .admin(admin)
                .build();

        return chatRoomRepository.save(chatRoom);
    }


    // ✅ 특정 채팅방의 메시지 조회 (사용자는 자신의 채팅방 메시지만 볼 수 있음)
    public List<ChatMessage> getMessages(String roomId) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new EntityNotFoundException("채팅방을 찾을 수 없습니다."));

        return chatMessageRepository.findByChatRoom(chatRoom);
    }

    // ✅ 관리자는 모든 사용자의 메시지를 조회 가능
    public List<ChatMessage> getAllMessages() {
        return chatMessageRepository.findAll();
    }
}
