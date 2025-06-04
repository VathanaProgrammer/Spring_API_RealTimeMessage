package com.RealTimeMessage.start.RealTimeMessage.repositorys;

import com.RealTimeMessage.start.RealTimeMessage.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);
}
