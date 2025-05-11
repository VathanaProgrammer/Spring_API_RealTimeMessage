package com.RealTimeMessage.start.RealTimeMessage.controllers;

import com.RealTimeMessage.start.RealTimeMessage.models.Post;
import com.RealTimeMessage.start.RealTimeMessage.services.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/create")
    public ResponseEntity<?> createPost(
            @RequestParam("userId") Long userId,
            @RequestParam("description") String description,
            @RequestParam(value = "imageFiles[]", required = false) List<MultipartFile> images
    ) {
        try {
            Post createdPost = postService.createPost(userId, description, images != null ? images : List.of());
            return ResponseEntity.ok(createdPost);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }
}
