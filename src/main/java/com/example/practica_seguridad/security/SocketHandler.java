package com.example.practica_seguridad.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.web.socket.CloseStatus;

@Component
public class SocketHandler extends TextWebSocketHandler {
    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    private static final Logger logger = LoggerFactory.getLogger(SocketHandler.class);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("Connection created. Session ID: {}", session.getId());
        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        logger.info("Message incoming: {}", message.getPayload());
        for (WebSocketSession webSocketSession : sessions) {
            try {
                webSocketSession.sendMessage(message);
            } catch (IOException e) {
                logger.error("Error sending message to session {}: {}", webSocketSession.getId(), e.getMessage());
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info("Connection closed. Session ID: {}", session.getId());
        sessions.remove(session);
    }
}