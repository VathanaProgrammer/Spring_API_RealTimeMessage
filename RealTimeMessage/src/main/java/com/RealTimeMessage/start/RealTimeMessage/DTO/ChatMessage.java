package com.RealTimeMessage.start.RealTimeMessage.DTO;

import java.util.List;

public class ChatMessage {
    private String senderName;
    private String content;
    private String receiverName;
    private List<String> images;

    // Getters and setters
    public String getReceiverName() { return receiverName; }
    public void setReceiverName(String receiverName) { this.receiverName = receiverName; }

    public String getSenderName() { return this.senderName; }
    public void setSenderName(String senderName) { this.senderName = senderName; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }
}



