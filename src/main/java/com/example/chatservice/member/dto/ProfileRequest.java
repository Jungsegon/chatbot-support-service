package com.example.chatservice.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileRequest {
    private String nickname;
    private String photo;
}
