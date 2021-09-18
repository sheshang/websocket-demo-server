package com.codeforgeyt.wschatapplication.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    RestSocketHandler getRestSocketHandler;

    private final List<WebSocketSession> webSocketSessions = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        webSocketSessions.add(session);
        this.getRestSocketHandler.getWebSocketSessions().add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        for(WebSocketSession webSocketSession : webSocketSessions){
            webSocketSession.sendMessage(message);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        webSocketSessions.remove(session);
        this.getRestSocketHandler.getWebSocketSessions().remove(session);
    }
}
