package com.RealTimeMessage.start.RealTimeMessage.repositorys;

import com.RealTimeMessage.start.RealTimeMessage.models.Like;
import com.RealTimeMessage.start.RealTimeMessage.models.Post;

import com.RealTimeMessage.start.RealTimeMessage.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LikeRepo extends JpaRepository<Like, Long> {
    List<Like> findByPostId(Long likeId);
    Optional<Like> findByUserAndPost(User user, Post post);
    long countByPost(Post post);
    void deleteByUserAndPost(User user, Post post);
}
