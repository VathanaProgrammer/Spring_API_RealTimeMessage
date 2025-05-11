package com.RealTimeMessage.start.RealTimeMessage.services;

import com.RealTimeMessage.start.RealTimeMessage.models.Image;
import com.RealTimeMessage.start.RealTimeMessage.models.Post;
import com.RealTimeMessage.start.RealTimeMessage.models.User;
import com.RealTimeMessage.start.RealTimeMessage.repositorys.ImageRepo;
import com.RealTimeMessage.start.RealTimeMessage.repositorys.PostRepo;
import com.RealTimeMessage.start.RealTimeMessage.repositorys.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private PostRepo postRepository;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private ImageRepo imageRepository;


    public Post createPost(Long userId, String description, List<MultipartFile> imageFiles) throws Exception {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new Exception("User not found");
        }

        User user = userOptional.get();
        Post post = new Post();
        post.setDescription(description);
        post.setUser(user);

        List<Image> imageList = new ArrayList<>();

        // Make sure the upload folder exists
        Path uploadDir = Paths.get(uploadPath);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        for (MultipartFile file : imageFiles) {
            if (!file.isEmpty()) {
                // Generate a unique filename to prevent conflicts
                String uniqueName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path filePath = uploadDir.resolve(uniqueName);

                // Save file to the server
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                // Create Image entity
                Image image = new Image();
                image.setUrl("/upload/" + uniqueName); // This is the URL/path that frontend can use
                image.setPost(post);
                imageList.add(image);
            }
        }
        System.out.println("Received " + imageFiles.size() + " images");

        post.setImages(imageList);

        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByIdDesc();
    }
}
