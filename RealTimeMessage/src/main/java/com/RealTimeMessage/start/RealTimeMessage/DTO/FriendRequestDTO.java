package com.RealTimeMessage.start.RealTimeMessage.DTO;

import com.RealTimeMessage.start.RealTimeMessage.models.FriendRequest;

public class FriendRequestDTO {
    private Long requestId;
    private Long senderId;
    private String senderName;
    private String senderProfileImage;
    private String status;

    public FriendRequestDTO(FriendRequest request) {
        this.requestId = request.getId();
        this.senderId = request.getSender().getId();
        this.senderName = request.getSender().getUsername();
        this.senderProfileImage = request.getSender().getProfileImage();
        this.status = request.getStatus();
    }

    // Getters and setters...
}

