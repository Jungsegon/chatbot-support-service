package com.example.chatservice.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO {
    private String sender;
    private String content;
    private String roomId;
}
