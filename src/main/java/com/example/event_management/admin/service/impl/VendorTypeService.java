package com.example.event_management.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.event_management.common.AppStatus;
import com.example.event_management.projection.admin.VendorTypeProjection;
import com.example.event_management.repository.IVendorTypeRepo;

@Service
public class VendorTypeService {
    
    @Autowired
    IVendorTypeRepo iVendorTypeRepo;

    public List<VendorTypeProjection> getAllVendorTypes() {
        return iVendorTypeRepo.findByStatus(AppStatus.CommonStatus.ACTIVE);
    }
}
