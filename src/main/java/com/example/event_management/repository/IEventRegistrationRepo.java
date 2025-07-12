package com.example.event_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.event_management.entity.EventRegistration;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@Repository
public interface IEventRegistrationRepo extends JpaRepository<EventRegistration, Long> {

    boolean existsByRegistrationId(String registrationId);

    EventRegistration findByRegistrationId(String registrationId);

    //find by eventId
    Page<EventRegistration> findByEventId(Long eventId, Pageable pageable);

}
