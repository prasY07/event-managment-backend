package com.example.event_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.event_management.entity.Event;
import com.example.event_management.entity.EventService;

@Repository
public interface IEventServiceRepo extends JpaRepository<EventService, Long> {

    List<EventService> findByEventId(Event event);

    @Query(value="Select * from event_services where service_id in :id",nativeQuery=true)
    List<EventService> getAllEventServices (@Param("id") List<Long> id);
}
