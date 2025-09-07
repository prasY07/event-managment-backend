package com.example.event_management.repository;

import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.event_management.entity.MemberTypeServiceAccess;


@Repository
public interface IMemberTypeServiceAccessRepo extends JpaRepository<MemberTypeServiceAccess, Long> {
    

    @Query(value="Select count(*) from member_type_service_access where event_id = :event_id AND day_id = :dayId ", nativeQuery = true)
    int findByEventIdAndDayId(@Param("eventId") Long eventId, @Param("dayId") Long dayId);

    @Modifying
    @Transactional
    @Query(value="DELETE FROM member_type_service_access where event_id = :event_id AND day_id = :dayId ", nativeQuery = true)
    void deleteByEventIdAndDayId(@Param("eventId") Long eventId, @Param("dayId") Long dayId);

}
