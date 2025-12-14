package com.example.event_management.repository;

import com.example.event_management.entity.WeddingFunctionNotification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IWeddingFunctionNotificationRepository extends JpaRepository<WeddingFunctionNotification, Long> {

    Page<WeddingFunctionNotification>
    findByWeddingMaster_IdAndDeletedFalse(Long weddingId, Pageable pageable);}
