package com.RealTimeMessage.start.RealTimeMessage.controllers;

import com.RealTimeMessage.start.RealTimeMessage.DTO.ChatMessage;
import com.RealTimeMessage.start.RealTimeMessage.DTO.SseEmitterPool;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final SseEmitterPool emitterPool;

    public ChatController(SseEmitterPool emitterPool) {
        this.emitterPool = emitterPool;
    }

    // Existing stream endpoint for SSE
    @GetMapping("/stream/{username}")
    public SseEmitter stream(@PathVariable String username) {
        SseEmitter emitter = new SseEmitter();
        emitterPool.addEmitter(username, emitter);

        emitter.onCompletion(() -> emitterPool.removeEmitter(username));
        emitter.onTimeout(() -> emitterPool.removeEmitter(username));

        try {
            // Send a test message to indicate connection
            Map<String, String> message = new HashMap<>();
            message.put("message", "Connected to the chat stream");
            emitter.send(SseEmitter.event().data(message));
        } catch (IOException e) {
            emitter.completeWithError(e);
        }

        return emitter;
    }

    // POST endpoint to handle sending messages
    @PostMapping("/sendToUser")
    public ResponseEntity<String> sendMessage(@RequestBody ChatMessage msg) {
        // Send the message to both the sender and receiver through SSE

        // Broadcasting message to the sender
        emitterPool.sendMessage(msg.getSender(), msg);

        // Broadcasting message to the receiver
        emitterPool.sendMessage(msg.getReceiver(), msg);

        return ResponseEntity.ok("Message sent successfully");
    }

}

