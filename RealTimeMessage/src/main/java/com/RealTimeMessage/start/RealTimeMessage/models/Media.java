package com.RealTimeMessage.start.RealTimeMessage.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url; // relative URL or path to the file

    private String type; // e.g., "image", "video"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @JsonBackReference
    private Post post;

    public Media() {}

    public Media(String url, String type, Post post) {
        this.url = url;
        this.type = type;
        this.post = post;
    }

    public void setPost(Post post) { this.post = post; }
    // Getters and setters
    public Long getId() { return id; }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public Post getPost() { return post; }

}
