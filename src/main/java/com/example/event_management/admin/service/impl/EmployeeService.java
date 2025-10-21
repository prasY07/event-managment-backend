package com.example.event_management.admin.service.impl;

import com.example.event_management.admin.dto.response.EmployeeListResponse;
import com.example.event_management.admin.dto.response.UserListResponse;
import com.example.event_management.common.response.PaginationResponse;
import com.example.event_management.entity.Employee;
import com.example.event_management.entity.User;
import com.example.event_management.repository.IEmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    IEmployeeRepo iEmployeeRepo;


    public PaginationResponse<List<EmployeeListResponse>> getAllEmployeesWithPagination(int page, int size) {
        Pageable pageable = (Pageable) PageRequest.of(page, size);
        Page<Employee> userPage = iEmployeeRepo.findAll(pageable);

        List<EmployeeListResponse> users = userPage.getContent()
                .stream()
                .map(this::createEmployeeListResponse)
                .toList();

        return new PaginationResponse<>(
                users,
                page,
                size,
                userPage.getTotalElements(),
                userPage.getTotalPages());
    }


    private EmployeeListResponse createEmployeeListResponse(Employee employee) {
        return new EmployeeListResponse(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getPhoneNumber(),
                employee.getCountry().getCountryCode(),
                employee.getStatus());
    }
}
