package com.RealTimeMessage.start.RealTimeMessage.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String uploadPath = Paths.get("D:/spring/Spring_API_RealTimeMessage/RealTimeMessage/upload").toUri().toString();

        registry.addResourceHandler("/upload/**")
                .addResourceLocations(uploadPath);
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:5173", "http://192.168.18.61:5173", "http://172.20.10.2:5173", "http://192.168.12.174:5173")  // Change this to your Vue app's URL
//                .allowedMethods("GET", "POST", "PUT", "DELETE")
//                .allowedHeaders("*")
//                .allowCredentials(true);
//    }


}
