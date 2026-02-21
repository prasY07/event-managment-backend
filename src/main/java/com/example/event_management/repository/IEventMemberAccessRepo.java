package com.example.event_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional; // <-- Required for transaction management

import com.example.event_management.entity.EventMemberAccess;
import com.example.event_management.entity.EventMemberType;

public interface IEventMemberAccessRepo extends JpaRepository<EventMemberAccess, Long> {
    @Modifying
    @Transactional
    @Query(value = "Delete From event_member_access where event_id = :event_id and member_type_id = :member_id", nativeQuery = true)
    int deleteData(@Param("event_id") Long event_id, @Param("member_id") Long member_id);

    @Query(value = "SELECT member_type_id , event_id , GROUP_CONCAT(event_access_type_id)  FROM event_member_access WHERE event_id = :eventId GROUP BY member_type_id, event_id", nativeQuery = true)
    List<Object[]> findAccessTypesByEventId(@Param("eventId") Long eventId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM event_member_access  WHERE member_type_id = :memberTypeId", nativeQuery = true)
    void deleteByMemberTypeId(@Param("memberTypeId") Long memberTypeId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM event_member_access  WHERE event_access_type_id = :eventAccessType", nativeQuery = true)
    void deleteByAccessTypeId(@Param("eventAccessType") Long eventAccessType);

    List<EventMemberAccess> findByMemberTypeId(EventMemberType eventMemberType);

    @Query(value = "SELECT event_access_type_id FROM event_member_access WHERE event_id = :eventId AND member_type_id = :memberTypeId", nativeQuery = true)
    List<Long> getAllAccessId(@Param("eventId") Long eventId, @Param("memberTypeId") Long memberTypeId);

}
