package com.RealTimeMessage.start.RealTimeMessage.controllers;

import com.RealTimeMessage.start.RealTimeMessage.DTO.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import org.springframework.util.Assert;

@Controller
public class WebSocketChatController {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * This handles messages sent to "/app/chat.sendToUser".
     *
     * @param message   The incoming ChatMessage payload (must contain a "receiver" field).
     * @param principal The authenticated user who sent this message (never null if handshake succeeded).
     */
    @MessageMapping("/chat.sendToUser")
    public void sendToUser(
            @Payload ChatMessage message,
            Principal principal
    ) {
        // 1) The Principal must be non-null if handshake was authenticated.
        Assert.notNull(principal, "Principal (sender) must not be null");

        String senderUsername = principal.getName();
        System.out.println("principal.getName: " + senderUsername);
        message.setSenderName(senderUsername);
        // 2) The payload must specify a non-null "receiver"
        String recipientUsername = message.getReceiverName();
        Assert.notNull(recipientUsername, "ChatMessage.receiver must not be null");

        // 3) Finally send to the recipient’s PRIVATE queue: "/user/{recipient}/queue/messages"
        //    Make sure the frontend is subscribed to "/user/queue/messages"
        messagingTemplate.convertAndSendToUser(
                recipientUsername,    // who you are sending to (cannot be null)
                "/queue/messages",    // destination prefix on the user’s session
                message               // the actual ChatMessage object
        );
    }
}
