package com.RealTimeMessage.start.RealTimeMessage.DTO;

import org.springframework.web.multipart.MultipartFile;

public class UpdateUserRequest {
    private String username;
    private String email;
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
    private MultipartFile profileImage;

    // getters and setters

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){this.email = email; }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getCurrentPassword(){
        return this.currentPassword;
    }

    public void setCurrentPassword(String currentPassword){
        this.currentPassword = currentPassword;
    }

    public String getNewPassword(){
        return this.newPassword;
    }

    public void setNewPassword(String newPassword){
        this.newPassword = newPassword;
    }

    public String getConfirmPassword(){
        return this.confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword){
        this.confirmPassword = confirmPassword;
    }

    public MultipartFile getProfileImage(){
        return this.profileImage;
    }

    public void setProfileImage(MultipartFile profileImage){
        this.profileImage = profileImage;
    }
}