package com.RealTimeMessage.start.RealTimeMessage.controllers;

import com.RealTimeMessage.start.RealTimeMessage.DTO.ApiResponse;
import com.RealTimeMessage.start.RealTimeMessage.DTO.UpdateUserRequest;
import com.RealTimeMessage.start.RealTimeMessage.DTO.UserDTO;
import com.RealTimeMessage.start.RealTimeMessage.models.User;
import com.RealTimeMessage.start.RealTimeMessage.repositorys.UserRepo;
import com.RealTimeMessage.start.RealTimeMessage.services.FileStorageService;
import com.RealTimeMessage.start.RealTimeMessage.services.JWTService;
import com.RealTimeMessage.start.RealTimeMessage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private FileStorageService fileStorageService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage
    ) throws IOException {


        User registeredUser = userService.registerUser(username, password, email, profileImage);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Optional<User> existingUser = userService.findByEmail(user.getEmail());

        if (existingUser.isPresent() && existingUser.get().getPassword().equals(user.getPassword())) {
            return ResponseEntity.ok(existingUser.get()); // Return token as part of the response
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/friends")
    public ResponseEntity<List<UserDTO>> getUserFirsts(@AuthenticationPrincipal User user){
        Long userId = user.getId();
        System.out.println("user id "+userId);
        return ResponseEntity.ok(userService.getUserFirends(userId));
    }

    @GetMapping("/non-friends")
    public ResponseEntity<List<UserDTO>> getNoUserFriends(@AuthenticationPrincipal User user){
        Long userId = user.getId();
        return ResponseEntity.ok(userService.getNoUserFriends(userId));
    }

    @GetMapping("/current-user")
    public Map<String, String> currentUser(@AuthenticationPrincipal User user) {
        return Collections.singletonMap("username", user.getUsername());
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDTO>> getCurrentUser(
            @AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(false, "Not authenticated", null));
        }

        // Cast the principal to your User entity
        User user = (User) userDetails;

        // Wrap it in your DTO (this only pulls in id, username, email, profileImage, and friend usernames)
        UserDTO dto = new UserDTO(user);
        System.out.println(dto);
        return ResponseEntity.ok(new ApiResponse<>(true, "User fetched", dto));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser (
            @AuthenticationPrincipal User user,
            @ModelAttribute UpdateUserRequest request) throws IOException {

        User existUser = userService.findByEmail(user.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getCurrentPassword(), existUser.getPassword())) {
            return ResponseEntity.badRequest().body("Current password is incorrect");
        }

        existUser.setUsername(request.getUsername());
        existUser.setEmail(request.getEmail());

        // Handle image (optional)
        MultipartFile image = request.getProfileImage();
        if (image != null && !image.isEmpty()) {
            // Save the image file and set the path or filename
            String imageName = fileStorageService.saveImageAndReturnFilename(image);
            existUser.setProfileImage("/upload/"+imageName);
        }

        if (!request.getNewPassword().isBlank()) {
            existUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
        }

        userRepo.save(existUser);
        return ResponseEntity.ok("Profile updated successfully");
    }


}
