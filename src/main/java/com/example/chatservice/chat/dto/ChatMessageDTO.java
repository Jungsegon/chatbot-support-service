package com.example.chatservice.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO {
    private String roomId;
    private String sender;
    private String recipient;
    private String content;
}
