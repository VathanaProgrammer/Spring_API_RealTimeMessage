package com.RealTimeMessage.start.RealTimeMessage.DTO;

import com.RealTimeMessage.start.RealTimeMessage.models.User;

// Nested DTO to avoid recursion or exposing sensitive data
public class FriendDTO {
    private Long id;
    private String username;
    private String profileImage;

    public FriendDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.profileImage = user.getProfileImage();
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getProfileImage() { return profileImage; }
}
