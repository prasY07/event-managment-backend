package com.example.event_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.event_management.entity.UserServiceUsage;

@Repository
public interface IUserServiceUsageRepo extends JpaRepository<UserServiceUsage, Long> {

    @Query(value="Select used_at from user_service_usage where event_id =:eventId AND service_access_id = :serviceAccessId" , nativeQuery=true )
    Boolean hasUserUsedService(@Param("eventId") Long eventId, @Param("serviceAccessId") Long serviceAccessId);

}
