package com.chat_service.handers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
public class WebSocketChatHandler extends TextWebSocketHandler {

    //연결된 클라이언트가 무엇인지 파악하는 로직
    //특정 클라이언트가 메시지를 보냈을때 전달하도록 함
    private final Map<String, WebSocketSession> webSocketSessionMap = new HashMap<>();

    //웹소켓 클라이언트가 서버로 연결을 한 이후에 시작
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("{} connected", session.getId());
        webSocketSessionMap.put(session.getId(), session);
    }

    //웹소켓 클라이언트에서 메시지가 왔을 경우 처리하는 로직
    //a라는 클라이언트에서 온 메시지를 다른 클라이언트에 전달
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
            throws Exception {
        log.info("{} sent: {}", session.getId(), message.getPayload());
        //다른 클라이언트에게 보내는 로직
        webSocketSessionMap.values().forEach(s -> {
                    try {
                        s.sendMessage(message);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }

    //서버의 웹소켓 클라이언트가 접속을 끊을때 사용
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
            throws Exception {
        log.info("{} disconnected", session.getId());
        webSocketSessionMap.remove(session.getId());
    }
}
