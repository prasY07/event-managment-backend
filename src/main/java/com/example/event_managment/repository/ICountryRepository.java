package com.example.event_managment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.event_managment.entity.Country;

@Repository
public interface ICountryRepository extends JpaRepository<Country, Long> {
    List<Country> findAll();
}
