package com.example.event_management.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.event_management.common.AppStatus;
import com.example.event_management.entity.Event;

@Repository
public interface IEventRepo extends JpaRepository<Event, Long> {

    Page<Event> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM events WHERE event_id = :eventId", nativeQuery = true)
    Event findEventByUUID(@Param("eventId") String eventId);

    @Query(value = "SELECT * FROM events WHERE event_id = :eventUUID", nativeQuery = true)
    Optional<Event> findByEventUUID(String eventUUID);

    boolean existsByEventId(String eventId);

    @Query(value = "SELECT * FROM events WHERE start_date = :date AND  status = :status", nativeQuery = true)
    List<Event> getTodayActiveEvent(@Param("date") LocalDate date ,@Param("status") String status);

    @Query(value = "SELECT * FROM events WHERE id = :eventId", nativeQuery = true)
    Event findEventByID(@Param("eventId") Long eventId);

    @Query("SELECT COUNT(e) FROM Event e")
    Long countTotalEvents();
}
