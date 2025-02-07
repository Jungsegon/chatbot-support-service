package com.example.chatservice.chat;

import com.example.chatservice.chat.Entity.ChatRoom;
import com.example.chatservice.chat.dto.ChatMessageDTO;
import com.example.chatservice.chat.service.ChatRoomService;
import com.example.chatservice.security.JwtProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@Component // Spring이 이 클래스를 관리하도록 설정
@RequiredArgsConstructor // final 필드 자동 생성자 주입 (의존성 해결)
public class ChatWebSocketHandler extends TextWebSocketHandler {
    private final Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>(); // 사용자 세션 저장
    private final Map<String, String> sessionUserMap = new ConcurrentHashMap<>(); // sessionId -> username 매핑
    private WebSocketSession adminSession = null; // 관리자 세션 (1명)

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ChatRoomService chatRoomService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String username = extractUsername(session);
        if ("admin".equals(username)) {
            adminSession = session;
            log.info("✅ 관리자 WebSocket 연결됨: {}", username);
        } else {
            userSessions.put(username, session);
            sessionUserMap.put(session.getId(), username);
            log.info("✅ 사용자 WebSocket 연결됨: {}", username);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("📩 메시지 수신: {}", payload);

        ChatMessageDTO chatMessage = objectMapper.readValue(payload, ChatMessageDTO.class);
        String senderAccount = "admin".equals(chatMessage.getSender()) ? "admin" : sessionUserMap.get(session.getId());

        if (senderAccount == null) {
            log.warn("❌ 알 수 없는 사용자({})로부터 메시지 수신, 처리 불가.", session.getId());
            return;
        }

        // ✅ recipient 설정: sender가 admin이면 roomId(사용자 account), sender가 사용자면 recipient=admin
        String recipient = "admin".equals(chatMessage.getSender()) ? chatMessage.getRoomId() : "admin";
        chatMessage.setRecipient(recipient); // ✅ recipient 설정 추가

        // ✅ 기존 로직을 유지하면서 roomId를 정확하게 설정
        if (!"admin".equals(chatMessage.getSender())) {
            chatMessage.setRoomId(senderAccount); // ✅ 사용자일 때만 roomId를 설정
        }

        chatRoomService.saveMessage(chatMessage.getRoomId(), chatMessage.getSender(), chatMessage.getContent());

        // ✅ 메시지를 보낼 대상(roomId)에 따라 전송 방식 결정
        if ("admin".equals(chatMessage.getSender())) {
            sendToUser(chatMessage); // ✅ admin이 보낸 경우 사용자에게 전송
        } else {
            sendToAdmin(chatMessage); // ✅ 일반 사용자가 보낸 경우 admin에게 전송
        }
    }

    private void sendToUser(ChatMessageDTO chatMessage) throws IOException {
        String userAccount = chatMessage.getRecipient();

        if ("admin".equals(userAccount)) {
            log.warn("❌ 잘못된 메시지 전송 시도: 관리자(admin)에게는 sendToUser()를 사용할 수 없음.");
            return;
        }

        WebSocketSession userSession = userSessions.get(userAccount); // ✅ 사용자 세션 찾기

        if (userSession != null && userSession.isOpen()) {
            userSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(chatMessage)));
            log.info("📨 메시지 사용자({})에게 전송: {}", userAccount, chatMessage.getContent());
        } else {
            log.warn("❌ 사용자({})의 WebSocket 세션을 찾을 수 없음. 현재 userSessions 목록: {}", userAccount, userSessions.keySet());
        }
    }

    private void sendToAdmin(ChatMessageDTO chatMessage) throws IOException {
        if (adminSession != null && adminSession.isOpen()) {
            adminSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(chatMessage)));
            log.info("📨 메시지 관리자(admin)에게 전송: {}", chatMessage.getContent());
        } else {
            log.warn("❌ 관리자(admin)의 WebSocket 세션을 찾을 수 없음.");
        }
    }


    private String extractUsername(WebSocketSession session) {
        String query = session.getUri().getQuery();
        if (query != null && query.startsWith("username=")) {
            return query.substring(9);
        }
        return "unknown";
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String username = sessionUserMap.get(session.getId());
        userSessions.remove(username);
        sessionUserMap.remove(session.getId());
        if ("admin".equals(username)) {
            adminSession = null;
        }
        log.info("❌ WebSocket 연결 종료: {}", username);
    }




}