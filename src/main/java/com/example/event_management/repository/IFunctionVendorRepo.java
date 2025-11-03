package com.example.event_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.event_management.entity.WFunctionVendorMapping;

@Repository
public interface IFunctionVendorRepo extends JpaRepository<WFunctionVendorMapping , Long> {

    
} 
