package com.RealTimeMessage.start.RealTimeMessage.repositorys;

import com.RealTimeMessage.start.RealTimeMessage.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);



    //select * from user => go inside user_friends table => select * from user_friends where friend_id = userId
    @Query("SELECT u FROM User u JOIN u.friends f WHERE f.id = :userId")
    List<User> findFriendsOfUser(@Param("userId") Long userId);

    @Query("SELECT u FROM User u WHERE u.id <> :userId AND u.id NOT IN " +
            "(SELECT f.id FROM User current JOIN current.friends f WHERE current.id = :userId)")
    List<User> findNotFriendsOfUser(@Param("userId") Long userId);

}
