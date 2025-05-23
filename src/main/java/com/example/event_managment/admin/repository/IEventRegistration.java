package com.example.event_managment.admin.repository;

import com.example.event_managment.common.entity.EventRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEventRegistration extends JpaRepository<EventRegistration , Long> {
}
