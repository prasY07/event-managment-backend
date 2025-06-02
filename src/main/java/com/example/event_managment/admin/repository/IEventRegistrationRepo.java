package com.example.event_managment.admin.repository;

import com.example.event_managment.entity.EventRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEventRegistrationRepo extends JpaRepository<EventRegistration , Long> {
   
    boolean existsByRegistrationId(String registrationId);

    EventRegistration findByRegistrationId(String registrationId);

}
