package com.RealTimeMessage.start.RealTimeMessage.services;

import com.RealTimeMessage.start.RealTimeMessage.DTO.UserDTO;
import com.RealTimeMessage.start.RealTimeMessage.models.User;
import com.RealTimeMessage.start.RealTimeMessage.repositorys.UserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;

    @Value("${upload.path}")
    private String uploadPath;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public Optional<User> findByEmail(String Email){
        return userRepo.findByEmail((Email));
    }

    public User registerUser(String username, String password, String email, MultipartFile profileImage) throws IOException {
        if (userRepo.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already in use");
        }

        User user = new User();
        user.setUsername(username);

        String encoder = passwordEncoder.encode(password);
        user.setPassword(encoder);

        user.setEmail(email);
        user.setPosts(Collections.emptyList());
        user.setFriends(Collections.emptyList());

        if (profileImage != null && !profileImage.isEmpty()) {
            String filename = UUID.randomUUID() + "_" + profileImage.getOriginalFilename();
            Path uploadDir = Paths.get(uploadPath);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
            Path imagePath = uploadDir.resolve(filename);
            Files.copy(profileImage.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
            user.setProfileImage("/upload/" + filename);
        }
        System.out.println("Saved user ID: " + user.getId());
        return userRepo.save(user);


    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepo.findAll();
        System.out.println("Users found: " + users.size()); // Log the size
        return users.stream()
                .map(UserDTO::new) // Convert User to UserDTO
                .collect(Collectors.toList());
    }

    public List<UserDTO> getUserFirends(Long userId){
        return userRepo.findFriendsOfUser(userId)
                .stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    public List<UserDTO> getNoUserFriends(Long UserId){
        return userRepo.findNotFriendsOfUser(UserId)
                .stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }


    public boolean existsById(Long receiverId) {
        return userRepo.findById(receiverId).isPresent();
    }
}
