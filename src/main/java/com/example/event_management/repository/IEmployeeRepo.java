package com.example.event_management.repository;

import com.example.event_management.entity.Employee;
import com.example.event_management.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmployeeRepo extends JpaRepository<Employee,Long> {

    Page<Employee> findAll(Pageable pageable);

}
