package com.RealTimeMessage.start.RealTimeMessage.repositorys;

import com.RealTimeMessage.start.RealTimeMessage.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<Message, Long> {
}
