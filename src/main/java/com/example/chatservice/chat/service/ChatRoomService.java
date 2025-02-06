package com.example.chatservice.chat.service;

import com.example.chatservice.chat.ChatMessage;
import com.example.chatservice.chat.ChatRoom;
import com.example.chatservice.chat.repository.ChatMessageRepository;
import com.example.chatservice.chat.repository.ChatRoomRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    // 채팅방 생성 , ChatRoom 엔터티에서 create를 사용하여 레포지토리에 저장.
    public ChatRoom createRoom(String name) {
        return chatRoomRepository.save(ChatRoom.create(name));
    }

    // 채팅방 찾기
    public ChatRoom findRoom(String roomId) {
        return chatRoomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new EntityNotFoundException("채팅방을 찾을 수 없습니다."));
    }

    // 채팅방 내용 불러오기
    public List<ChatMessage> getMessage(String roomId){
        ChatRoom chatRoom = findRoom(roomId);
        return chatMessageRepository.findByChatRoom(chatRoom);
    }

    // 나갈 때 채팅방 메시지 저장하기
    public ChatMessage saveMessage(String roomId, String sender, String content){
        ChatRoom chatRoom = findRoom(roomId);
        ChatMessage message = ChatMessage.builder()
                .sender(sender)
                .content(content)
                .chatRoom(chatRoom)
                .build();
        return chatMessageRepository.save(message);
    }
}
