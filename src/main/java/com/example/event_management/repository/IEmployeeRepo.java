package com.example.event_management.repository;

import com.example.event_management.entity.Employee;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeRepo extends JpaRepository<Employee, Long> {

    // Page<Employee> findAll(Pageable pageable);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumberAndCountry_Id(String phoneNumber, Long countryId);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByPhoneNumberAndCountry_IdAndIdNot(String phoneNumber, Long countryId, Long id);

    @Query(value = "SELECT * FROM employees e where e.status = 'ACTIVE' AND e_type=:eType",nativeQuery = true)
    List<Employee> findAllEmployee(@Param("eType") String eType);

}
