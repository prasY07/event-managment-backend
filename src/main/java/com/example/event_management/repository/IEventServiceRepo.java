package com.example.event_management.repository;

import com.example.event_management.entity.EventService;
import com.example.event_management.entity.Event;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEventServiceRepo extends JpaRepository<EventService, Long> {

    List<EventService> findByEventId(Event event);
}
