package com.RealTimeMessage.start.RealTimeMessage.services;

import com.RealTimeMessage.start.RealTimeMessage.models.FriendRequest;
import com.RealTimeMessage.start.RealTimeMessage.models.User;
import com.RealTimeMessage.start.RealTimeMessage.repositorys.FriendRequestRepository;
import com.RealTimeMessage.start.RealTimeMessage.repositorys.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FriendRequestService {

    @Autowired
    private FriendRequestRepository repository;

    @Autowired
    private UserRepo userRepo;


    public FriendRequest sendRequest(User sender, User receiver) {
        if (repository.existsBySenderIdAndReceiverId(sender.getId(), receiver.getId())) {
            throw new IllegalStateException("Request already sent");
        }

        FriendRequest request = new FriendRequest();
        request.setSender(sender);
        request.setReceiver(receiver);
        request.setStatus("pending");

        return repository.save(request);
    }


    public List<FriendRequest> getPendingRequests(Long receiverId) {
        return repository.findByReceiverIdAndStatus(receiverId, "pending");
    }

    public FriendRequest respondToRequest(Long requestId, String status) {
        Optional<FriendRequest> optionalRequest = repository.findById(requestId);
        if (!optionalRequest.isPresent()) {
            throw new RuntimeException("Friend request not found");
        }

        FriendRequest FriendRequest = optionalRequest.get();
        FriendRequest.setStatus(status);

        if (status.equalsIgnoreCase("accepted")) {
            User sender = FriendRequest.getSender();
            User receiver = FriendRequest.getReceiver();

            // Add each other as friends
            sender.getFriends().add(receiver);
            receiver.getFriends().add(sender);

            // Save both users
            userRepo.save(sender);
            userRepo.save(receiver);
        }

        return repository.save(FriendRequest);
    }


    // Get all received friend requests for a user
    public List<FriendRequest> getRequestsReceivedByUser(Long userId) {
        return repository.findByReceiverId(userId);
    }

    // Optional: Get all sent friend requests by a user
    public List<FriendRequest> getRequestsSentByUser(Long userId) {
        return repository.findBySenderId(userId);
    }

    // Optional: Get all requests (if admin or for testing)
    public List<FriendRequest> getAllRequests() {
        return repository.findAll();
    }
}

