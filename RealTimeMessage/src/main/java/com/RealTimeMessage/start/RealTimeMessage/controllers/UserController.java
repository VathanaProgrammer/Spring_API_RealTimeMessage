package com.RealTimeMessage.start.RealTimeMessage.controllers;

import com.RealTimeMessage.start.RealTimeMessage.DTO.UserDTO;
import com.RealTimeMessage.start.RealTimeMessage.models.User;
import com.RealTimeMessage.start.RealTimeMessage.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

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

    @GetMapping("/getUserFriends")
    public ResponseEntity<List<UserDTO>> getUserFirsts(@RequestParam Long UserId){
        return ResponseEntity.ok(userService.getUserFirends(UserId));
    }

    @GetMapping("/getNoUserFriends")
    public ResponseEntity<List<UserDTO>> getNoUserFriends(@RequestParam Long UserId){
        return ResponseEntity.ok(userService.getNoUserFriends(UserId));
    }

}
