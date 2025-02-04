package com.example.chatservice.chat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
@Slf4j
public class ChatWebSocketHandler implements WebSocketHandler {
    private final ObjectMapper objectMapper;
    private final Map<String, String> faqResponses = new HashMap<>();

    public ChatWebSocketHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        // 미리 정의된 질문-답변 (FAQ)
        faqResponses.put("운영 시간", "저희 챗봇은 24시간 운영됩니다!");
        faqResponses.put("배송 조회", "배송 상태를 확인하시려면 주문번호를 입력해주세요.");
        faqResponses.put("고객센터", "고객센터 전화번호는 1588-0000 입니다.");
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("새로운 WebSocket 연결: " + session.getId());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String payload = (String) message.getPayload();
        System.out.println("사용자로부터 메시지 수신: " + payload);

        JsonNode jsonNode = objectMapper.readTree(payload);
        String userMessage = jsonNode.get("message").asText();

        // 기본 응답 설정
        String botResponse = faqResponses.getOrDefault(userMessage, "죄송합니다. 해당 질문에 대한 답변을 찾을 수 없습니다.");

        // 응답을 JSON 형식으로 변환하여 클라이언트에 전송
        Map<String, String> response = new HashMap<>();
        response.put("userMessage", userMessage);
        response.put("botResponse", botResponse);

        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(response)));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.err.println("WebSocket 오류 발생: " + exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("WebSocket 연결 종료: " + session.getId());
    }

    // 🔹 WebSocketHandler의 필수 구현 메서드 (누락된 메서드 추가)
    @Override
    public boolean supportsPartialMessages() {
        return false;  // 부분 메시지 지원하지 않음
    }
}
*/

@Slf4j
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override // 웹 소켓 연결시
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
    }

    @Override // 데이터 통신시
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
    }

    @Override // 웹소켓 통신 에러시
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
    }

    @Override // 웹 소켓 연결 종료시
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }
}