package com.RealTimeMessage.start.RealTimeMessage.controllers;

import com.RealTimeMessage.start.RealTimeMessage.DTO.CommentDTO;
import com.RealTimeMessage.start.RealTimeMessage.DTO.CommentRequest;
import com.RealTimeMessage.start.RealTimeMessage.models.Comment;
import com.RealTimeMessage.start.RealTimeMessage.models.User;
import com.RealTimeMessage.start.RealTimeMessage.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/add")
    public ResponseEntity<?> addComment(
            @AuthenticationPrincipal User user,
            @RequestBody CommentRequest commentRequest
    ) {
        try {
            Comment comment = commentService.addComment(user, commentRequest.getPostId(), commentRequest.getText());
            // Wrap in DTO before returning
            CommentDTO commentDTO = new CommentDTO(comment);
            return ResponseEntity.ok(commentDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    @GetMapping("/post/{postId}")
    public ResponseEntity<?> getComments(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsForPost(postId));
    }
}

