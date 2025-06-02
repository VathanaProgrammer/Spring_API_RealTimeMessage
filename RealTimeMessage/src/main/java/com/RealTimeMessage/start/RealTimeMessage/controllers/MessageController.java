package com.RealTimeMessage.start.RealTimeMessage.controllers;

import com.RealTimeMessage.start.RealTimeMessage.models.Message;
import com.RealTimeMessage.start.RealTimeMessage.models.User;
import com.RealTimeMessage.start.RealTimeMessage.repositorys.MessageRepo;
import com.RealTimeMessage.start.RealTimeMessage.repositorys.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MessageRepo messageRepo;
    @PostMapping
    public ResponseEntity<Message> saveMessage(@AuthenticationPrincipal User user, @RequestBody Message message) {
        message.setTimestamp(LocalDateTime.now());

        // Set sender info from logged-in user
        Long senderId = user.getId();
        message.setSenderId(senderId);
        message.setSenderName(user.getUsername());

        // Fetch receiver's username by receiverId and set it
        Long receiverId = message.getReceiverId();
        userRepo.findById(receiverId).ifPresent(receiverUser -> {
            message.setReceiverName(receiverUser.getUsername());
        });

        Message savedMessage = messageRepo.save(message);
        return ResponseEntity.ok(savedMessage);
    }


    @GetMapping("/conversation")
    public ResponseEntity<List<Message>> getConversation(
            @RequestParam Long userId,
            @AuthenticationPrincipal User user) {
        Long currentUserId = user.getId(); // this gets the authenticated user's ID (from token)
        System.out.println("currect user id (fetch conversation) : " + currentUserId);
        List<Message> messages = messageRepo.findMessagesBetween(currentUserId, userId);
        System.out.println(messages);
        return ResponseEntity.ok(messages);
    }

}
