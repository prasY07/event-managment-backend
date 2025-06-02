package com.example.event_managment.admin.repository;

import com.example.event_managment.entity.Event;
import com.example.event_managment.entity.EventAccessType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEventAccessTypeRepo extends JpaRepository<EventAccessType, Long> {
    List<EventAccessType> findByEventId(Event event);

}
