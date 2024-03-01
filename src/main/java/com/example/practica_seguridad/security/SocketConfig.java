package com.example.practica_seguridad.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class SocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        try {
            registry.addHandler(new SocketHandler(), "/websocket").setAllowedOrigins("http://127.0.0.1:5500");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
