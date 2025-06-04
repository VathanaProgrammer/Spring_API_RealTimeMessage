package com.RealTimeMessage.start.RealTimeMessage.controllers;

import com.RealTimeMessage.start.RealTimeMessage.DTO.LikeRequest;
import com.RealTimeMessage.start.RealTimeMessage.models.Like;
import com.RealTimeMessage.start.RealTimeMessage.models.Post;
import com.RealTimeMessage.start.RealTimeMessage.models.User;
import com.RealTimeMessage.start.RealTimeMessage.repositorys.LikeRepo;
import com.RealTimeMessage.start.RealTimeMessage.repositorys.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    @Autowired
    private LikeRepo likeRepo;

    @Autowired
    private PostRepo postRepo;

    @PostMapping
    public ResponseEntity<?> toggleLike(
            @AuthenticationPrincipal User user,
            @RequestBody LikeRequest request
    ) {
        System.out.println(user.getUsername()+"like the post");
        Optional<Post> optionalPost = postRepo.findById(request.getPostId());
        if (optionalPost.isEmpty()) {
            return ResponseEntity.badRequest().body("Post not found");
        }

        Post post = optionalPost.get();
        Optional<Like> existingLike = likeRepo.findByUserAndPost(user, post);

        if (existingLike.isPresent()) {
            // Unlike
            likeRepo.delete(existingLike.get());
            return ResponseEntity.ok("Post unliked");
        } else {
            // Like
            Like newLike = new Like(user, post);
            likeRepo.save(newLike);
            return ResponseEntity.ok("Post liked");
        }
    }

    @GetMapping("/count")
    public ResponseEntity<?> getLikeCount(@RequestParam Long postId) {
        Optional<Post> post = postRepo.findById(postId);
        if (post.isEmpty()) return ResponseEntity.badRequest().body("Post not found");

        long count = likeRepo.countByPost(post.get());
        return ResponseEntity.ok(count);
    }
}
