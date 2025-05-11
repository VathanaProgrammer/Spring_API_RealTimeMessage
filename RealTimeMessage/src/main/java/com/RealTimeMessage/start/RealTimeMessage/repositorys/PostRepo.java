package com.RealTimeMessage.start.RealTimeMessage.repositorys;

import com.RealTimeMessage.start.RealTimeMessage.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Long userId);
    List<Post> findAllByOrderByIdDesc();
}
