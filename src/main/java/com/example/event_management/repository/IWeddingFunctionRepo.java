package com.example.event_management.repository;

import com.example.event_management.projection.admin.WeddingFunctionProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.event_management.entity.WeddingFunction;
import org.springframework.stereotype.Repository;

@Repository
public interface IWeddingFunctionRepo extends JpaRepository<WeddingFunction , Long>  {

    Page<WeddingFunctionProjection> findByWeddingMaster_WeddingId(
            Long weddingId,
            Pageable pageable
    );
    
}
