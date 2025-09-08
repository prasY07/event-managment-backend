package com.example.event_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.event_management.entity.Event;
import com.example.event_management.entity.EventDay;

@Repository
public interface IEventDayRepo extends JpaRepository<EventDay , Long> {

    List<EventDay> findByEvent(Event event);
}
