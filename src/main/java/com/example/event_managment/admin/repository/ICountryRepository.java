package com.example.event_managment.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.example.event_managment.entity.Country;

@Repository
public interface ICountryRepository extends JpaRepository<Country, Long> {

    List<Country> findAll();

}
