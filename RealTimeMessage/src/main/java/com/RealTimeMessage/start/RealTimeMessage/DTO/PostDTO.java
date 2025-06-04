package com.RealTimeMessage.start.RealTimeMessage.DTO;

import com.RealTimeMessage.start.RealTimeMessage.models.Media;
import com.RealTimeMessage.start.RealTimeMessage.models.Post;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class PostDTO {
    private Long id;
    private String description;
    private UserDTO user;
    private List<MediaDTO> mediaFiles; // Updated from imageUrls
    private List<CommentDTO> comments;
    private int likeCount;
    private String createdAt;

    public PostDTO(Post post) {
        this.id = post.getId();
        this.description = post.getDescription();
        this.user = new UserDTO(post.getUser());

        this.mediaFiles = post.getMediaFiles() != null
                ? post.getMediaFiles().stream()
                .map(MediaDTO::new)
                .collect(Collectors.toList())
                : List.of();

        this.comments = post.getComments() != null
                ? post.getComments().stream()
                .map(CommentDTO::new)
                .collect(Collectors.toList())
                : List.of();

        this.likeCount = post.getLikes() != null ? post.getLikes().size() : 0;

        this.createdAt = post.getCreatedAt() != null
                ? post.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                : null;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public UserDTO getUser() {
        return user;
    }

    public List<MediaDTO> getMediaFiles() {
        return mediaFiles;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
