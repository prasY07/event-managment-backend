package com.example.event_management.admin.controller;

import com.example.event_management.admin.dto.AddUpdateEmployeeDto;
import com.example.event_management.admin.dto.response.EmployeeListResponse;
import com.example.event_management.admin.service.impl.EmployeeService;
import com.example.event_management.common.response.ApiResponse;
import com.example.event_management.common.response.PaginationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/employee/")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;


    @GetMapping("/list")
    public ResponseEntity<ApiResponse<PaginationResponse<List<EmployeeListResponse>>>> allEmployeesWithPagination(
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


     @GetMapping("/all-employees")
    public ResponseEntity<ApiResponse<List<EmployeeListResponse>>> allEmployees(@RequestParam String eType) {
        List<EmployeeListResponse> res = employeeService.getAllEmployees(eType);
        return ApiResponse.success(
                "Employee List",res);
    }

     @PostMapping("/create")
    public ResponseEntity<ApiResponse<String>> createEmployee(@RequestBody AddUpdateEmployeeDto addUpdateEmployeeDto) {
        Boolean res = employeeService.createEmployee(addUpdateEmployeeDto);
        if (!res) {
        return ApiResponse.error("OOPS Something went wrong", null, HttpStatus.INTERNAL_SERVER_ERROR);
            
        }
        return ApiResponse.success("New Employee Created successfully", null);
    }


    @PostMapping("/{id}/update")
    public ResponseEntity<ApiResponse<String>> updateEmployee(@RequestBody AddUpdateEmployeeDto addUpdateEmployeeDto, @PathVariable Long id) {
        Boolean res = employeeService.updateEmployee(addUpdateEmployeeDto, id);
        if (!res) {
        return ApiResponse.error("OOPS Something went wrong", null, HttpStatus.INTERNAL_SERVER_ERROR);
            
        }
        return ApiResponse.success("New Employee Created successfully", null);
    }

     @GetMapping("/{id}/update-status")
    public ResponseEntity<ApiResponse<String>> updateEmployeeStatus(@PathVariable Long id) {
        Boolean res = employeeService.updateStatus(id);
        if (!res) {
        return ApiResponse.error("OOPS Something went wrong", null, HttpStatus.INTERNAL_SERVER_ERROR);
            
        }
        return ApiResponse.success("New Employee Created successfully", null);
    }
}
