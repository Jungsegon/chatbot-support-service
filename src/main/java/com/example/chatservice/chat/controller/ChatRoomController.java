package com.example.chatservice.chat.controller;

import com.example.chatservice.SecurityCheckUtil;
import com.example.chatservice.chat.Entity.ChatMessage;
import com.example.chatservice.chat.Entity.ChatRoom;
import com.example.chatservice.chat.dto.ChatMessageDTO;
import com.example.chatservice.chat.dto.ChatRoomDTO;
import com.example.chatservice.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //REST API를 제공하는 컨트롤러
@RequestMapping("/chat")
@RequiredArgsConstructor // final 필드에 대한 생성자를 자동으로 생성
public class ChatRoomController {
    private final ChatRoomService chatRoomService;



//    @PostMapping("/send")
//    public ResponseEntity<String> sendMessage(@RequestBody ChatMessageDTO messageDTO, Authentication authentication) {
//        String userAccount = authentication.getName();
//        ChatRoom chatRoom = chatRoomService.createOrFindRoom(userAccount);
//
//        chatRoomService.saveMessage(chatRoom.getRoomId(), messageDTO.getSender(), messageDTO.getContent());
//
//        return ResponseEntity.ok("Message sent successfully");
//    }


    // 사용자가 자신의 채팅방 메시지 조회
    @GetMapping("/messages/{roomId}")
    public ResponseEntity<List<ChatMessage>> getMessages(@PathVariable String roomId){
        return ResponseEntity.ok(chatRoomService.getMessages(roomId));
    }


    // 관리자가 모든 채팅 메시지 조회
    @GetMapping("/messages/all")
    public ResponseEntity<List<ChatMessage>> getAllMessages() {
        return ResponseEntity.ok(chatRoomService.getAllMessages());
    }

    // 채팅방 조회 (사용자 1명 - 관리자 1명 1:1 채팅)
    @GetMapping("/room/{roomId}")
    public ResponseEntity<ChatRoomDTO> getRoom(@PathVariable String roomId) {
        ChatRoom chatRoom = chatRoomService.createOrFindRoom(roomId);
        return ResponseEntity.ok(new ChatRoomDTO(chatRoom.getRoomId(), chatRoom.getName()));
    }

    // 관리자가 모든 채팅방 조회
    @GetMapping("/rooms")
    public ResponseEntity<List<ChatRoomDTO>> getAllChatRooms(){
        List<ChatRoomDTO> chatRooms = chatRoomService.getAllChatRooms();
        return ResponseEntity.ok(chatRooms);
    }

}
