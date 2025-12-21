package com.example.event_management.web.repo;

import com.example.event_management.entity.WeddingGuest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IWeddingGuestRepository extends JpaRepository<WeddingGuest,Long> {
    List<WeddingGuest> findByWeddingId(Long weddingId);
}
