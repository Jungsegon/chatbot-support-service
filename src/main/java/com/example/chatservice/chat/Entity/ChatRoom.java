package com.example.chatservice.chat.Entity;

import com.example.chatservice.member.Member;
import jakarta.persistence.*;
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

    @Column(unique = true, nullable = false)
    private String roomId; // 고유 채팅방ID

    @Column(nullable = false)
    private String name; // 채팅방 이름

    // ✅ 사용자 1명 (User) in 채팅방
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "account", nullable = false)
    private Member user;

    // ✅ 관리자 1명 (Admin) in 채팅방
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", referencedColumnName = "account", nullable = false)
    private Member admin;
}
