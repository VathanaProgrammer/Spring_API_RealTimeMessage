package com.RealTimeMessage.start.RealTimeMessage.DTO;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Component
public class SseEmitterPool {

    private final ConcurrentHashMap<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public void addEmitter(String username, SseEmitter emitter) {
        emitters.put(username, emitter);
    }

    public void removeEmitter(String username) {
        emitters.remove(username);
    }

    public SseEmitter getEmitter(String username) {
        return emitters.get(username);
    }

    public void sendMessage(String username, Object message) {
        SseEmitter emitter = emitters.get(username);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().data(message));
            } catch (Exception e) {
                emitter.completeWithError(e);
                emitters.remove(username); // Clean up dead emitters
            }
        }
    }
}



