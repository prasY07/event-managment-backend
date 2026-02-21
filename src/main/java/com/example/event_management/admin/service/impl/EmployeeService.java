package com.example.event_management.admin.service.impl;

import com.example.event_management.admin.dto.AddUpdateEmployeeDto;
import com.example.event_management.admin.dto.response.EmployeeListResponse;
import com.example.event_management.common.AppStatus;
import com.example.event_management.common.response.PaginationResponse;
import com.example.event_management.entity.Country;
import com.example.event_management.entity.Employee;
import com.example.event_management.repository.ICountryRepo;
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

    @Autowired
    ICountryRepo icountryRepo;

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

    public List<EmployeeListResponse> getAllEmployees(String eType) {
        List<Employee> userPage = iEmployeeRepo.findAllEmployee(eType);

        List<EmployeeListResponse> users = userPage
                .stream()
                .map(this::createEmployeeListResponse)
                .toList();

        return users;
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

    public Boolean createEmployee(AddUpdateEmployeeDto addUpdateEmployeeDto) {
        if (iEmployeeRepo.existsByEmail(addUpdateEmployeeDto.getEmail())) {
            throw new IllegalArgumentException("Email is already taken by another email");
        }

        if (iEmployeeRepo.existsByPhoneNumberAndCountry_Id(addUpdateEmployeeDto.getPhoneNumber(),
                addUpdateEmployeeDto.getCountryId())) {
            throw new IllegalArgumentException("Phone number with country code another user");
        }
        Country country = icountryRepo.findById(addUpdateEmployeeDto.getCountryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid country selected"));

        Employee employee = new Employee();
        employee.setName(addUpdateEmployeeDto.getName());
        employee.setEmail(addUpdateEmployeeDto.getEmail());
        employee.setPhoneNumber(addUpdateEmployeeDto.getPhoneNumber());
        employee.setCountry(country); // Correct way to set the entire country entity
        employee.setStatus(AppStatus.EmployeeStatus.INACTIVE);
        employee.setEType(addUpdateEmployeeDto.getEType());
        iEmployeeRepo.save(employee); // Save and get the saved entity with ID
        return Boolean.TRUE; // Convert to UserResponse and return
    }

    public Boolean updateEmployee(AddUpdateEmployeeDto addUpdateEmployeeDto, Long id) {

        // check emaployee exist or not
        Employee existingEmployee = iEmployeeRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with id: " + id));

        if (iEmployeeRepo.existsByEmailAndIdNot(addUpdateEmployeeDto.getEmail(), id)) {
            throw new IllegalArgumentException("Email is already taken by another email");
        }

        if (iEmployeeRepo.existsByPhoneNumberAndCountry_IdAndIdNot(addUpdateEmployeeDto.getPhoneNumber(),
                addUpdateEmployeeDto.getCountryId(), id)) {
            throw new IllegalArgumentException("Phone number with country code taken by another user");
        }
        Country country = icountryRepo.findById(addUpdateEmployeeDto.getCountryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid country selected"));

        existingEmployee.setName(addUpdateEmployeeDto.getName());
        existingEmployee.setEmail(addUpdateEmployeeDto.getEmail());
        existingEmployee.setPhoneNumber(addUpdateEmployeeDto.getPhoneNumber());
        existingEmployee.setCountry(country); // Correct way to set the entire country entity
                existingEmployee.setEType(addUpdateEmployeeDto.getEType());

        iEmployeeRepo.save(existingEmployee); // Save and get the saved entity with ID
        return Boolean.TRUE; // Convert to UserResponse and return
    }

    public Boolean updateStatus(Long id) {

        // check emaployee exist or not
        Employee existingEmployee = iEmployeeRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with id: " + id));

        if (existingEmployee.getStatus() == AppStatus.EmployeeStatus.ACTIVE) {
            existingEmployee.setStatus(AppStatus.EmployeeStatus.INACTIVE);
        } else {
            existingEmployee.setStatus(AppStatus.EmployeeStatus.ACTIVE);
        }
        iEmployeeRepo.save(existingEmployee); // Save and get the saved entity with ID
        return Boolean.TRUE; // Convert to UserResponse and return
    }

}
