package com.RealTimeMessage.start.RealTimeMessage.repositorys;

import com.RealTimeMessage.start.RealTimeMessage.models.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    List<FriendRequest> findByReceiverIdAndStatus(Long receiverId, String status);
    List<FriendRequest> findBySenderId(Long senderId);
    boolean existsBySenderIdAndReceiverId(Long senderId, Long receiverId);
    List<FriendRequest> findByReceiverId(Long receiverId);
}

