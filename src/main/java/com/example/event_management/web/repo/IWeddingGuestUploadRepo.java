package com.example.event_management.web.repo;

import com.example.event_management.entity.WeddingGuestUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWeddingGuestUploadRepo extends JpaRepository <WeddingGuestUpload,Long> {

    boolean existsByWeddingMaster_IdAndMobileNumber(Long weddingId, String mobileNumber);


     boolean existsByWeddingMaster_IdAndSide_SideId(
            Long weddingId,
            Integer sideId
    );
}
