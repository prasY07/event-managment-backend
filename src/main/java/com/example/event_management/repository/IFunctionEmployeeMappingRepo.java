package com.example.event_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.event_management.entity.WFunctionEmployeeMapping;

public interface IFunctionEmployeeMappingRepo extends JpaRepository<WFunctionEmployeeMapping , Long> {
    
}
