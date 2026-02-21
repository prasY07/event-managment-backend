package com.example.event_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.event_management.entity.MemberTypeServiceAccess;

import jakarta.transaction.Transactional;


@Repository
public interface IMemberTypeServiceAccessRepo extends JpaRepository<MemberTypeServiceAccess, Long> {
    

    @Query(value="Select count(*) from member_type_service_access where event_id = :eventId AND day_id = :dayId AND service_id = :serviceId ", nativeQuery = true)
    int findByEventIdAndDayId(@Param("eventId") Long eventId, @Param("dayId") Long dayId, @Param("serviceId") Long serviceId);

    @Modifying
    @Transactional
    @Query(value="DELETE FROM member_type_service_access where event_id = :eventId AND day_id = :dayId  and service_id = :serviceId", nativeQuery = true)
    void deleteByEventIdAndDayIdAndServiceId(@Param("eventId") Long eventId, @Param("dayId") Long dayId , @Param("serviceId") Long serviceId);


    @Query(value="Select * from member_type_service_access where event_id= :eventId AND  day_id = :dayId  AND member_type_id = :member_type_id ", nativeQuery=true)
    List<Long> getAllServiceId(@Param("eventId") Long eventId, @Param("dayId") Long dayId , @Param("member_type_id") Long member_type_id);

}
