package com.example.event_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.event_management.entity.WeddingSideMaster;
import com.example.event_management.projection.admin.WeddingSideMasterProjection;

public interface IWeddingSideMasterRepo extends JpaRepository<WeddingSideMaster, Integer> {

    
    @Query("SELECT wsm FROM WeddingSideMaster wsm WHERE wsm.sideName = :sideName")
    WeddingSideMaster findBySideName(String sideName);

    //Find all
    @Query("SELECT sideId as id, sideName as Name FROM WeddingSideMaster wsm where status = 'ACTIVE'")
    List<WeddingSideMasterProjection> findActiveMasterProjection();
} 
