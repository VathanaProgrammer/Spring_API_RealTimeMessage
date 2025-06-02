package com.RealTimeMessage.start.RealTimeMessage.Response;


import lombok.*;

@Data
public class JwtLoginResponse {
    private String token;
    private Long id;
    private String username;
    private String email;

    public JwtLoginResponse(String token, Long id, String username, String email) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
    }

    // Getters (important!)
    public String getToken() { return token; }
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
}
