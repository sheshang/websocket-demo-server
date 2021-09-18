package com.codeforgeyt.wschatapplication.handler;


import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class RestSocketHandler extends TextWebSocketHandler {

    private List<WebSocketSession> webSocketSessions = new ArrayList<>();
    private WebSocketClient webSocketClient = new StandardWebSocketClient();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        session = webSocketClient.doHandshake(this, new WebSocketHttpHeaders(), URI.create("ws://localhost:8080/chat/")).get();
        webSocketSessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        for (WebSocketSession webSocketSession : webSocketSessions) {
            webSocketSession.sendMessage(message);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        webSocketSessions.remove(session);
    }

    public void sendMessage(TextMessage message) throws IOException {
        for (WebSocketSession webSocketSession : webSocketSessions) {
            webSocketSession.sendMessage(message);
        }
    }

    public List<WebSocketSession> getWebSocketSessions() {
        return this.webSocketSessions;
    }
}
