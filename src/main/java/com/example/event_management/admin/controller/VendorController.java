package com.example.event_management.admin.controller;

import java.util.List;

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

import com.example.event_management.admin.dto.AddUpdateEmployeeDto;
import com.example.event_management.admin.dto.AddUpdateVendorDto;
import com.example.event_management.admin.dto.response.EmployeeListResponse;
import com.example.event_management.admin.dto.response.VendorListResponse;
import com.example.event_management.admin.service.impl.VendorService;
import com.example.event_management.common.response.ApiResponse;
import com.example.event_management.common.response.PaginationResponse;

@RestController
@RequestMapping("/api/admin/vendor")
public class VendorController {
    
    @Autowired
    VendorService vendorService;



    @GetMapping("/list")
    public ResponseEntity<ApiResponse<PaginationResponse<List<VendorListResponse>>>> allVendorWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PaginationResponse<List<VendorListResponse>> paginatedEmplouees = vendorService.getAllVendorWithPagination(page, size);
        return ApiResponse.successWithPagination(
                "Employee List",
                paginatedEmplouees.getItems(),
                paginatedEmplouees.getPage(),
                paginatedEmplouees.getSize(),
                paginatedEmplouees.getTotalElements(),
                paginatedEmplouees.getTotalPages());
    }

    //  @GetMapping("/all-active-vendor")
    // public ResponseEntity<ApiResponse<List<EmployeeListResponse>>> activeVendor() {
    //     List<EmployeeListResponse> res = vendorService.getAllActiveVendor();
    //     return ApiResponse.success(
    //             "Employee List",res);
    // }

     @PostMapping("/create")
    public ResponseEntity<ApiResponse<String>> createNewVendor(@RequestBody AddUpdateVendorDto addUpdateVendorDto) {
        Boolean res = vendorService.createNewVendor(addUpdateVendorDto);
        if (!res) {
        return ApiResponse.error("OOPS Something went wrong", null, HttpStatus.INTERNAL_SERVER_ERROR);
            
        }
        return ApiResponse.success("New Vendor Created successfully", null);
    }


    @PostMapping("/{id}/update")
    public ResponseEntity<ApiResponse<String>> updateVendor(@RequestBody AddUpdateVendorDto addUpdateVendorDto, @PathVariable Long id) {
        Boolean res = vendorService.updateVendor(addUpdateVendorDto, id);
        if (!res) {
        return ApiResponse.error("OOPS Something went wrong", null, HttpStatus.INTERNAL_SERVER_ERROR);
            
        }
        return ApiResponse.success("Vendor update successfully", null);
    }

     @GetMapping("/{id}/update-status")
    public ResponseEntity<ApiResponse<String>> updateVendorStatus(@PathVariable Long id) {
        Boolean res = vendorService.updateStatus(id);
        if (!res) {
        return ApiResponse.error("OOPS Something went wrong", null, HttpStatus.INTERNAL_SERVER_ERROR);
            
        }
        return ApiResponse.success("Vendor status update successfully", null);
    }
}
