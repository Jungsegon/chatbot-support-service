package com.example.chatservice.chat;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sender; // 보낸이
    private String content; // 메시지 내용

    @ManyToOne // 한채팅방에 메시지가 여러개 있으므로 다대일
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom; //메시지가 속한 채팅방
}
