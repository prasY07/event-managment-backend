package com.example.event_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.event_management.entity.Vendor;

public interface IVendorRepo extends JpaRepository<Vendor, Long> {


     boolean existsByEmail(String email);

    boolean existsByPhoneNumberAndCountry_Id(String phoneNumber, Long countryId);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByPhoneNumberAndCountry_IdAndIdNot(String phoneNumber, Long countryId, Long id);

    
}
