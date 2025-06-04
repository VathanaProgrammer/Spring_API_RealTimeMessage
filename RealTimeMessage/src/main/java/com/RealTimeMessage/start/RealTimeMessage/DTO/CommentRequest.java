package com.RealTimeMessage.start.RealTimeMessage.DTO;

public class CommentRequest {
    private Long postId;
    private String text;

    // Getters and setters
    public Long getPostId() {
        return postId;
    }
    public void setPostId(Long postId) {
        this.postId = postId;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
