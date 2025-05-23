package com.example.event_managment.admin.repository;

import com.example.event_managment.common.entity.Event;
import com.example.event_managment.common.entity.EventMemberType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IEventMemberType extends JpaRepository<EventMemberType, Long> {
    List<EventMemberType> findByEventId(Event event);

}
