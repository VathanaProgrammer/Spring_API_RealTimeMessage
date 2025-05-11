package com.RealTimeMessage.start.RealTimeMessage.DTO;

import com.RealTimeMessage.start.RealTimeMessage.models.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDTO {
    private Long id;
    private String username;
    private String profileImage;
    // Constructor
    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.profileImage = user.getProfileImage();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getProfileImage() {
        return profileImage;
    }
}
