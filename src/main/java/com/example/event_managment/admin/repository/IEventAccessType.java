package com.example.event_managment.admin.repository;

import com.example.event_managment.common.entity.Event;
import com.example.event_managment.common.entity.EventAccessType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEventAccessType extends JpaRepository<EventAccessType, Long> {
    List<EventAccessType> findByEventId(Event event);

}
