package com.RealTimeMessage.start.RealTimeMessage.controllers;

import com.RealTimeMessage.start.RealTimeMessage.DTO.UserDTO;
import com.RealTimeMessage.start.RealTimeMessage.models.LoginRequest;
import com.RealTimeMessage.start.RealTimeMessage.models.User;
import com.RealTimeMessage.start.RealTimeMessage.services.JWTService;
import com.RealTimeMessage.start.RealTimeMessage.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword())
            );

            User user = userService.findByEmail(loginRequest.getEmail()).orElseThrow();
            System.out.println("LoginController: user found, generating token...");

            String token = jwtService.generateToken(user.getEmail());
            System.out.println("LoginController: token generated: " + token);

            jwtService.setTokenCookie(response, token); // Set as HttpOnly cookie
            System.out.println("LoginController: cookie set.");

            return ResponseEntity.ok(token); // Return only the token
        }  catch (BadCredentialsException ex) {             // <— Print full stack trace
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid username or password"));  // <— Include exception message
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
            HttpServletResponse response
    ){

       try {
           User registeredUser = userService.registerUser(username, password, email, profileImage);
           String token = jwtService.generateToken(registeredUser.getEmail());

           jwtService.setTokenCookie(response, token); // Set as HttpOnly cookie

           return ResponseEntity.ok(token); // Return only the token
       }catch (IllegalArgumentException e){
           return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                   "success", false,
                   "message", e.getMessage()
           ));
       }catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "success", false,
                "message", "Registration failed"
        ));
    }

    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        // Clear the JWT cookie
        ResponseCookie deleteCookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(false) // set to false if you're not using HTTPS (e.g. localhost)
                .sameSite("Lax")
                .path("/")
                .maxAge(0) // delete the cookie immediately
                .build();

        response.addHeader("Set-Cookie", deleteCookie.toString());

        return ResponseEntity.ok("Logged out successfully");
    }

}
