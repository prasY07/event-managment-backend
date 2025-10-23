package com.example.event_management.repository;

import com.example.event_management.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeRepo extends JpaRepository<Employee, Long> {

    Page<Employee> findAll(Pageable pageable);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumberAndCountry_Id(String phoneNumber, Long countryId);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByPhoneNumberAndCountry_IdAndIdNot(String phoneNumber, Long countryId, Long id);

    @Query("SELECT e FROM Employee e where e.status = 'ACTIVE'")
    Page<Employee> findAllEmployee();

}
