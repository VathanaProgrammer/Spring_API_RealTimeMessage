package com.RealTimeMessage.start.RealTimeMessage.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.RealTimeMessage.start.RealTimeMessage.models.Image;

@Repository
public interface ImageRepo extends JpaRepository<Image, Long> {}