package com.RealTimeMessage.start.RealTimeMessage.DTO;

import com.RealTimeMessage.start.RealTimeMessage.models.Media;

public class MediaDTO {
    private String url;
    private String type; // "image" or "video"

    public MediaDTO(Media media) {
        this.url = media.getUrl();
        this.type = media.getType();
    }

    public String getUrl() {
        return url;
    }

    public String getType() {
        return type;
    }
}
