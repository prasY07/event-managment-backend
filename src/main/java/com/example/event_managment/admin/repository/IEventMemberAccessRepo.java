package com.example.event_managment.admin.repository;

import com.example.event_managment.common.entity.EventMemberAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional; // <-- Required for transaction management

import java.util.List;

public interface IEventMemberAccessRepo extends JpaRepository<EventMemberAccess , Long>
{
    @Modifying
    @Transactional
    @Query(value = "Delete From event_member_access where event_id = :event_id and member_type_id = :member_id",nativeQuery = true)
    int deleteData(@Param("event_id") Long event_id, @Param("member_id") Long member_id );

    @Query(value = "SELECT member_type_id , event_id , GROUP_CONCAT(event_access_type_id)  FROM event_member_access WHERE event_id = :eventId GROUP BY member_type_id, event_id", nativeQuery = true)
    List<Object[]> findAccessTypesByEventId(@Param("eventId") Long eventId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM event_member_access  WHERE member_type_id = :memberTypeId",nativeQuery = true)
    void deleteByMemberTypeId(@Param("memberTypeId") Long memberTypeId);


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM event_member_access  WHERE event_access_type_id = :eventAccessType",nativeQuery = true)
    void deleteByAccessTypeId(@Param("eventAccessType") Long eventAccessType);

}
