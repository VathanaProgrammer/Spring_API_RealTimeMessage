package com.RealTimeMessage.start.RealTimeMessage.DTO;

import com.RealTimeMessage.start.RealTimeMessage.models.Comment;

public class CommentDTO {
    private Long id;
    private String content;
    private UserDTO user;  // Assuming each comment has a user who made the comment

    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        if (comment.getUser() != null) {
            this.user = new UserDTO(comment.getUser());
        }
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public UserDTO getUser() {
        return user;
    }
}
