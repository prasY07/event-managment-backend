package com.example.event_managment.admin.repository;

import com.example.event_managment.admin.entity.Event;
import com.example.event_managment.admin.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IEventRepo extends JpaRepository<Event, Long> {

    Page<Event> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM users WHERE id = :id", nativeQuery = true)
    Event findEvent(@Param("id") Long id);

}
