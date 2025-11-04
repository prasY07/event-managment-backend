package com.example.event_management.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.event_management.entity.WeddingMaster;
import com.example.event_management.projection.admin.WeddingListShortProjection;

public interface IWeddingMasterRepo extends JpaRepository<WeddingMaster , Long> {

    @Query("SELECT w.id AS id, w.groomName AS groomName, w.brideName AS brideName, w.weddingDate AS weddingDate , w.coupleDisplayName AS coupleName " +
           "FROM WeddingMaster w")
     Page<WeddingListShortProjection> findWeddingShortResponse(Pageable pageable);

        boolean existsByWeddingId(String weddingId);
}
