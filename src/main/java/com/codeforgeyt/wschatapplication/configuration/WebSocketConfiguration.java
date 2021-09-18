package com.codeforgeyt.wschatapplication.configuration;

import com.codeforgeyt.wschatapplication.handler.ChatWebSocketHandler;
import com.codeforgeyt.wschatapplication.handler.RestSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    private final static String CHAT_ENDPOINT = "/chat";
    private final static String CHAT_ENDPOINT_WITH_SLASH = "/chat/";

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry
                .addHandler(getRestSocketHandler(), CHAT_ENDPOINT_WITH_SLASH)
                .addHandler(getChatWebSocketHandler(), CHAT_ENDPOINT)
                .setAllowedOrigins("*")
                .withSockJS()
                .setHeartbeatTime(20);
    }

    @Bean
    public WebSocketHandler getChatWebSocketHandler(){
        return new ChatWebSocketHandler();
    }

    @Bean
    public RestSocketHandler getRestSocketHandler(){
        try {
            return new RestSocketHandler();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
