package com.example.event_management.repository;

import com.example.event_management.entity.EventService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEventServiceRepo extends JpaRepository<EventService, Long> {
}
