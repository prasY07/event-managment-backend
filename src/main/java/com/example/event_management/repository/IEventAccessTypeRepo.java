package com.example.event_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.event_management.entity.Event;
import com.example.event_management.entity.EventAccessType;

@Repository
public interface IEventAccessTypeRepo extends JpaRepository<EventAccessType, Long> {
    List<EventAccessType> findByEventId(Event event);

}
