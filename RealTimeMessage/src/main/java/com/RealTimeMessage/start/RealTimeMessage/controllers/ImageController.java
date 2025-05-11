package com.RealTimeMessage.start.RealTimeMessage.controllers;

import com.RealTimeMessage.start.RealTimeMessage.models.Image;
import com.RealTimeMessage.start.RealTimeMessage.services.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/upload/{postId}")
    public ResponseEntity<?> uploadImage(
            @RequestParam("file") MultipartFile file,
            @PathVariable Long postId) {
        try {
            Image savedImage = imageService.saveImage(file, postId);
            return ResponseEntity.ok(savedImage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Image upload failed: " + e.getMessage());
        }
    }
}
