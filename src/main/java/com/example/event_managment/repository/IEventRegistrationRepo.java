package com.example.event_managment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.event_managment.entity.EventRegistration;

@Repository
public interface IEventRegistrationRepo extends JpaRepository<EventRegistration, Long> {

    boolean existsByRegistrationId(String registrationId);

    EventRegistration findByRegistrationId(String registrationId);

}
