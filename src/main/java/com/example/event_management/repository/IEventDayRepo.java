package com.example.event_management.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.event_management.entity.Event;
import com.example.event_management.entity.EventDay;

@Repository
public interface IEventDayRepo extends JpaRepository<EventDay , Long> {

    List<EventDay> findByEvent(Event event);

    @Query(value="Select * from event_days where event_date = :date AND event_id = :eventId ", nativeQuery=true)
    EventDay checkDayExists(@Param("date") LocalDate date , @Param("eventId") Long eventId );
}
