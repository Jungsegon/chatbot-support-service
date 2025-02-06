package com.example.chatservice.chat;

import com.example.chatservice.chat.dto.ChatMessageDTO;
import com.example.chatservice.chat.service.ChatRoomService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
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
@Component // Spring이 이 클래스를 관리하도록 설정
@RequiredArgsConstructor // final 필드 자동 생성자 주입 (의존성 해결)
public class ChatWebSocketHandler extends TextWebSocketHandler {

    // WEBSOCKET 세션 저장하는 MAP, 브로드캐스트용
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    // 채팅방별 세션을 저장하는 맵 (roomId -> (sessionId -> WebSocketSession))
    private final Map<String, Map<String, WebSocketSession>> chatRooms = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON 파싱을 위함.
    private final ChatRoomService chatRoomService;

    @Override // 웹 소켓 연결시
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("새로운 webSocket 연결: {} ", session.getId());
//        super.afterConnectionEstablished(session);
    }

    @Override // 데이터 통신시
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload(); // 클라이언트가 보낸 메시지 내용
        log.info("메시지 수신 : {}", payload); // 로그 출력

        /*JsonNode jsonNode = objectMapper.readTree(payload);  //JSON 메시지 파싱
        String roomId = jsonNode.get("roomId").asText();
        String sender = jsonNode.get("sender").asText();
        String content = jsonNode.get("content").asText();
        //sender와 content값 추출하기 + roomId도 추출

        chatRoomService.saveMessage(roomId, sender, content);
        // 메시지를 json 형태로 변환, 모든 세션에 전송
        String responseMessage = objectMapper.writeValueAsString(Map.of(
                "roomId", roomId,
                "sender", sender,
                "content", content
        ));*/

        // JSON 메시지를 DTO로 변환
        ChatMessageDTO chatMessage = objectMapper.readValue(payload, ChatMessageDTO.class);

        // 메시지 저장 (필요한 경우 DB 저장)
        chatRoomService.saveMessage(chatMessage.getRoomId(), chatMessage.getSender(), chatMessage.getContent());

        // JSON 형태로 변환 후 같은 채팅방의 모든 세션에 전송
        String responseMessage = objectMapper.writeValueAsString(chatMessage);

        // 모든 웹소켓 세션에 메시지를 전송함.
//        broadcast(responseMessage);

        // 특정 roomId에 보냄.
        sendToRoom(chatMessage.getRoomId(), responseMessage);
    }

    private void sendToRoom(String roomId, String message) throws IOException {
        Map<String, WebSocketSession> sessions = chatRooms.get(roomId); // 1 채팅방에 속한 세션 목록 가져오기
        if(sessions != null){  // 2 해당 채팅방이 존재하는지 확인
            for(WebSocketSession session : sessions.values()) { // 3 해당 채팅방에 있는 모든 사용자에게 메시지 전송
                if(session.isOpen()){ // 4 연결이 유지된 세션만 메시지 전송
                    session.sendMessage(new TextMessage(message)); // 5 WebSocket을 통해 메시지 전송
                }
            }
        }
    }

    // 모든 웹소켓 세션에 메시지를 전송함.
    private void broadcast(String message) throws Exception {
        for (WebSocketSession session : sessions.values()) {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(message));
            }
        }
    }


    @Override // 웹소켓 통신 에러시
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
//        super.handleTransportError(session, exception);
        log.error("웹 소켓 오류 발생 : {} ", exception.getMessage());

    }

    @Override // 웹 소켓 연결 종료시
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        super.afterConnectionClosed(session, status);
        log.error("웹 소켓 연결 종료 : {} ", session.getId());
    }
}