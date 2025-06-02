package com.RealTimeMessage.start.RealTimeMessage.repositorys;

import com.RealTimeMessage.start.RealTimeMessage.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m WHERE (m.senderId = :sender1 AND m.receiverId = :receiver1) OR (m.senderId = :receiver1 AND m.receiverId = :sender1) ORDER BY m.timestamp ASC")
    List<Message> findMessagesBetween(@Param("sender1") Long sender1, @Param("receiver1") Long receiver1);


}
