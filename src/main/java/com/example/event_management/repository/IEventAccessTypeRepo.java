package com.example.event_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.event_management.entity.Event;
import com.example.event_management.entity.EventAccessType;

@Repository
public interface IEventAccessTypeRepo extends JpaRepository<EventAccessType, Long> {
    List<EventAccessType> findByEventId(Event event);

    @Query(value="select * from event_access_types where id in :id",nativeQuery=true)
    List<EventAccessType> getAllAccessList(@Param("id") List<Long> accessId);



}
