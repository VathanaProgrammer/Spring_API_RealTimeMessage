package com.RealTimeMessage.start.RealTimeMessage.controllers;

import com.RealTimeMessage.start.RealTimeMessage.models.FriendRequest;
import com.RealTimeMessage.start.RealTimeMessage.models.User;
import com.RealTimeMessage.start.RealTimeMessage.services.FriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/friend-requests")
public class FriendRequestController {

    @Autowired
    private FriendRequestService service;

    @PostMapping("/send")
    public FriendRequest sendRequest(@RequestParam Long senderId, @RequestParam Long receiverId) {
        User sender = new User(); sender.setId(senderId);
        User receiver = new User(); receiver.setId(receiverId);
        return service.sendRequest(sender, receiver);
    }

    @GetMapping("/pending")
    public List<FriendRequest> getPendingRequests(@RequestParam Long receiverId) {
        return service.getPendingRequests(receiverId);
    }

    @PostMapping("/respond")
    public FriendRequest respondToRequest(@RequestParam Long requestId, @RequestParam String status) {
        return service.respondToRequest(requestId, status);
    }

    // Get requests received by a user
    @GetMapping("/received")
    public List<FriendRequest> getReceivedRequests(@RequestParam Long userId) {
        return service.getRequestsReceivedByUser(userId);
    }

    // Optional: Get requests sent by a user
    @GetMapping("/sent")
    public List<FriendRequest> getSentRequests(@RequestParam Long userId) {
        return service.getRequestsSentByUser(userId);
    }

    // Optional: Admin or all requests
    @GetMapping("/all")
    public List<FriendRequest> getAllRequests() {
        return service.getAllRequests();
    }
}

