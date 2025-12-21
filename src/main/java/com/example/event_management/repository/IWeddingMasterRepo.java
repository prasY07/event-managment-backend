package com.example.event_management.repository;

import com.example.event_management.projection.admin.WeddingFunctionProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.event_management.entity.WeddingMaster;
import com.example.event_management.projection.admin.WeddingListShortProjection;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IWeddingMasterRepo extends JpaRepository<WeddingMaster , Long> {

    @Query("""
SELECT
    w.id AS id,
    w.groomName AS groomName,
    w.brideName AS brideName,
    w.weddingDate AS weddingDate,
    w.coupleDisplayName AS coupleName,
    w.weddingStatus AS weddingStatus,
    w.weddingId AS weddingId,
    CASE
        WHEN w.weddingCard IS NULL OR TRIM(w.weddingCard) = ''
        THEN NULL
        ELSE CONCAT(:baseUrl, w.weddingCard)
    END AS weddingCard
FROM WeddingMaster w
""")
    Page<WeddingListShortProjection> findWeddingShortResponse(@Param("baseUrl") String baseUrl , Pageable pageable);



    boolean existsByWeddingId(String weddingId);

    @Query(value = "select wedding_card from wedding_master where wedding_id = :id" , nativeQuery = true)
    String getWeddingCard(@Param("id") String id);



}
