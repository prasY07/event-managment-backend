package com.example.event_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.event_management.entity.Country;

@Repository
public interface ICountryRepo extends JpaRepository<Country, Long> {
    List<Country> findAll();
}
