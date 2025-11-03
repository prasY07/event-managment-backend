package com.example.event_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.event_management.entity.WeddingMaster;

public interface IWeddingMasterRepo extends JpaRepository<WeddingMaster , Long> {

        boolean existsByWeddingId(String weddingId);
}
