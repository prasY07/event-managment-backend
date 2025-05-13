package com.example.event_managment.admin.repository;

import com.example.event_managment.admin.entity.Event;
import com.example.event_managment.admin.entity.EventAccessType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEventAccessType extends JpaRepository<EventAccessType, Long> {
    List<EventAccessType> findByEventId(Event event);

}
