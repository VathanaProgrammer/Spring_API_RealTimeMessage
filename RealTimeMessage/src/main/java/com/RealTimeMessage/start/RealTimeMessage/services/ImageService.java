package com.RealTimeMessage.start.RealTimeMessage.services;

import com.RealTimeMessage.start.RealTimeMessage.models.Image;
import com.RealTimeMessage.start.RealTimeMessage.models.Post;
import com.RealTimeMessage.start.RealTimeMessage.repositorys.ImageRepo;
import com.RealTimeMessage.start.RealTimeMessage.repositorys.PostRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class ImageService {

    @Value("${upload.path}")
    private String uploadPath;

    private final ImageRepo imageRepository;
    private final PostRepo postRepository;

    public ImageService(ImageRepo imageRepository, PostRepo postRepository) {
        this.imageRepository = imageRepository;
        this.postRepository = postRepository;
    }

    public Image saveImage(MultipartFile file, Long postId) throws IOException {
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path imagePath = Paths.get(uploadPath, filename);

        Files.copy(file.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Image image = new Image();
        image.setUrl("/uploads/" + filename);  // public path
        image.setPost(post);

        return imageRepository.save(image);
    }
}

