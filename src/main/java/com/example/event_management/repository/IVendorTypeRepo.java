package com.example.event_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.event_management.common.AppStatus;
import com.example.event_management.entity.VendorType;
import com.example.event_management.projection.admin.VendorTypeProjection;

@Repository
public interface IVendorTypeRepo extends JpaRepository<VendorType, Long> {

        // @Query(value = "Select name from vendor_types where status=:status")
        List<VendorTypeProjection> findByStatus(AppStatus.CommonStatus status);

    
}
