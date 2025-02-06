package com.example.chatservice.chat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Entity
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomId; // 채팅방 고유ID
    private String name; // 채팅방 이름

    public static ChatRoom create(String name){
        return ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .name(name)
                .build();
    }
}
