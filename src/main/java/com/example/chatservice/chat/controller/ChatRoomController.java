package com.example.chatservice.chat.controller;

import com.example.chatservice.SecurityCheckUtil;
import com.example.chatservice.chat.ChatMessage;
import com.example.chatservice.chat.ChatRoom;
import com.example.chatservice.chat.dto.ChatRoomDTO;
import com.example.chatservice.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //REST API를 제공하는 컨트롤러
@RequestMapping("/chat")
@RequiredArgsConstructor // final 필드에 대한 생성자를 자동으로 생성
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @GetMapping("/test")
    public ResponseEntity<String> testSecurity() {
        SecurityCheckUtil.checkAuthentication(); // 🔹 현재 사용자 인증 정보 출력
        return ResponseEntity.ok("Security 체크 완료");
    }

    @PostMapping("/room")
    public ResponseEntity<ChatRoomDTO> createRoom(@RequestBody String name) {
        ChatRoom chatRoom = chatRoomService.createRoom(name);
        return ResponseEntity.ok(new ChatRoomDTO(chatRoom.getRoomId(), chatRoom.getName()));
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<ChatRoomDTO> getRoom(@PathVariable String roomId) {
        ChatRoom chatRoom = chatRoomService.findRoom(roomId);
        return ResponseEntity.ok(new ChatRoomDTO(chatRoom.getRoomId(), chatRoom.getName()));
    }

    @GetMapping("/room/{roomid}/messages")
    public ResponseEntity<List<ChatMessage>> getMessage (@PathVariable String roomId){
        return ResponseEntity.ok(chatRoomService.getMessage(roomId));
    }
}
