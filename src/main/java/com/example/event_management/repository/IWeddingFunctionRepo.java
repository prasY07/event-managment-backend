package com.example.event_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.event_management.entity.WeddingFunction;

public interface IWeddingFunctionRepo extends JpaRepository<WeddingFunction , Long>  {
    
}
