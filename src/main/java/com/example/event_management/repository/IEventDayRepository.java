package com.example.event_management.repository;

import com.example.event_management.entity.EventDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEventDayRepository extends JpaRepository<EventDay , Long> {
}
