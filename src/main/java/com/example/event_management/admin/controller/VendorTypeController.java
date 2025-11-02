package com.example.event_management.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.event_management.admin.service.impl.VendorTypeService;
import com.example.event_management.common.response.ApiResponse;
import com.example.event_management.projection.admin.VendorTypeProjection;

@RestController
@RequestMapping("/api/admin/vendor-type")
public class VendorTypeController {

    @Autowired
    VendorTypeService vendorTypeService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<VendorTypeProjection>>> getData()
    {
        List<VendorTypeProjection> res = vendorTypeService.getAllVendorTypes();

        return ApiResponse.success("Vendor types", res);
        
    }
    
}
