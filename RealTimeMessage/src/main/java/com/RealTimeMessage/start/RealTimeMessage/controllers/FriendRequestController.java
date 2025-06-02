package com.RealTimeMessage.start.RealTimeMessage.controllers;

import com.RealTimeMessage.start.RealTimeMessage.models.FriendRequest;
import com.RealTimeMessage.start.RealTimeMessage.models.User;
import com.RealTimeMessage.start.RealTimeMessage.services.FriendRequestService;
import com.RealTimeMessage.start.RealTimeMessage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/friend-requests")
public class FriendRequestController {


    @Autowired
    private FriendRequestService service;

    @Autowired
    private UserService userService;


    @PostMapping("/send")
    public ResponseEntity<?> sendRequest(
            @AuthenticationPrincipal User sender,
            @RequestParam Long receiverId
    ) {
        if (sender.getId().equals(receiverId)) {
            return ResponseEntity.badRequest().body("Cannot friend yourself.");
        }

        if (!userService.existsById(receiverId)) {
            return ResponseEntity.notFound().build();
        }

        service.sendRequest(sender.getId(), receiverId);

        return ResponseEntity.ok("Friend request sent.");
    }

    @GetMapping("/pending")
    public List<FriendRequest> getPendingRequests(@AuthenticationPrincipal User user) {
        Long receiverId = user.getId();
        return service.getPendingRequests(receiverId);
    }


    // 2a) Accept a friend request
    @PostMapping("/accept/{requestId}")
    public ResponseEntity<?> acceptRequest(@PathVariable Long requestId,
                                           @AuthenticationPrincipal User user) {
        // Optionally, verify that `user` is indeed the receiver of that requestId.
        service.respondToRequest(requestId, "accepted");
        return ResponseEntity.ok("Friend request accepted.");
    }

    // 2b) Reject a friend request
    @PostMapping("/reject/{requestId}")
    public ResponseEntity<?> rejectRequest(@PathVariable Long requestId,
                                           @AuthenticationPrincipal User user) {
        // Optionally, verify that `user` is indeed the receiver of that requestId.
        service.respondToRequest(requestId, "rejected");
        return ResponseEntity.ok("Friend request rejected.");
    }

    // Optional: Admin or all requests
    @GetMapping("/all")
    public List<FriendRequest> getAllRequests() {
        return service.getAllRequests();
    }
}

