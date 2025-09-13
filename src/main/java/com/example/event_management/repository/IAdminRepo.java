package com.example.event_management.repository;

import com.example.event_management.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAdminRepo extends JpaRepository<Admin, Long> {

    Admin findByEmail(String email);
}
