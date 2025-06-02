package com.example.event_managment.admin.repository;

import com.example.event_managment.entity.Event;
import com.example.event_managment.entity.EventMemberType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface IEventMemberTypeRepo extends JpaRepository<EventMemberType, Long> {
    List<EventMemberType> findByEventId(Event event);
    
//    @Query("SELECT emt FROM EventMemberType emt WHERE emt.id = :id AND emt.event.id = :eventId")
//    Optional<EventMemberType> findByIdAndEventId(@Param("id") Long id, @Param("eventId") Long eventId);

    @Query(value = "SELECT * FROM event_member_types  WHERE id = :id AND event_id = :eventId",nativeQuery = true)
    Optional<EventMemberType> findByIdAndEventId(@Param("id") Long id, @Param("eventId") Long eventId);


}
