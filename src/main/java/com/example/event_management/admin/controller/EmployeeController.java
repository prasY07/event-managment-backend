package com.example.event_management.admin.controller;

import com.example.event_management.admin.dto.response.EmployeeListResponse;
import com.example.event_management.admin.service.impl.EmployeeService;
import com.example.event_management.common.response.ApiResponse;
import com.example.event_management.common.response.PaginationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;


    @GetMapping("/employee-list")
    public ResponseEntity<ApiResponse<PaginationResponse<List<EmployeeListResponse>>>> allUsersWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PaginationResponse<List<EmployeeListResponse>> paginatedEmplouees = employeeService.getAllEmployeesWithPagination(page, size);
        return ApiResponse.successWithPagination(
                "Employee List",
                paginatedEmplouees.getItems(),
                paginatedEmplouees.getPage(),
                paginatedEmplouees.getSize(),
                paginatedEmplouees.getTotalElements(),
                paginatedEmplouees.getTotalPages());
    }
}
