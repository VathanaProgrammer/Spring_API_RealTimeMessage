package com.RealTimeMessage.start.RealTimeMessage.services;

import com.RealTimeMessage.start.RealTimeMessage.DTO.PostDTO;
import com.RealTimeMessage.start.RealTimeMessage.models.Media;
import com.RealTimeMessage.start.RealTimeMessage.models.Post;
import com.RealTimeMessage.start.RealTimeMessage.models.User;
import com.RealTimeMessage.start.RealTimeMessage.repositorys.CommentRepo;
import com.RealTimeMessage.start.RealTimeMessage.repositorys.LikeRepo;
import com.RealTimeMessage.start.RealTimeMessage.repositorys.PostRepo;
import com.RealTimeMessage.start.RealTimeMessage.repositorys.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.util.*;

@Service
public class PostService {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private PostRepo postRepository;

    @Autowired
    private UserRepo userRepository;


    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private LikeRepo likeRepo;

    public Post createPost(User user, String description, List<MultipartFile> mediaFiles) throws Exception {
        if (user == null) {
            throw new Exception("User not authenticated");
        }

        Post post = new Post();
        post.setDescription(description);
        post.setUser(user);

        List<Media> mediaList = new ArrayList<>();

        Path uploadDir = Paths.get(uploadPath);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        for (MultipartFile file : mediaFiles) {
            if (!file.isEmpty()) {
                String originalName = file.getOriginalFilename();
                String extension = originalName.substring(originalName.lastIndexOf('.') + 1).toLowerCase();
                String uniqueName = UUID.randomUUID() + "_" + originalName;
                Path filePath = uploadDir.resolve(uniqueName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                Media media = new Media();
                media.setUrl("/upload/" + uniqueName);
                media.setType(isVideo(extension) ? "video" : "image");
                media.setPost(post);
                mediaList.add(media);
            }
        }

        post.setMediaFiles(mediaList);
        return postRepository.save(post);
    }

    private boolean isVideo(String extension) {
        return List.of("mp4", "webm", "ogg", "mov", "quicktime").contains(extension);
    }



    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAllByOrderByIdDesc();
        List<PostDTO> responseList = new ArrayList<>();

        for (Post post : posts) {
            // Instead of manually setting fields one by one, just:
            PostDTO dto = new PostDTO(post);
            responseList.add(dto);
        }

        return responseList;
    }

}
