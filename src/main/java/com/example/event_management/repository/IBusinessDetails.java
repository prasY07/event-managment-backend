package com.example.event_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.event_management.entity.BusinessDetails;


@Repository
public interface IBusinessDetails extends JpaRepository<BusinessDetails, Integer> {
    
}
