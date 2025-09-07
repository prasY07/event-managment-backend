package com.example.event_management.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.event_management.entity.Event;

@Repository
public interface IEventRepo extends JpaRepository<Event, Long> {

    Page<Event> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM events WHERE id = :id", nativeQuery = true)
    Event findEvent(@Param("id") Long id);      

    @Query(value = "SELECT * FROM events WHERE event_id = :eventUUID", nativeQuery = true)
    Optional<Event> findByEventUUID(String eventUUID);

    boolean existsByEventId(String eventId);



}
