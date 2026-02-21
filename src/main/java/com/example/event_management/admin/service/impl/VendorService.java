package com.example.event_management.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.event_management.admin.dto.AddUpdateVendorDto;
import com.example.event_management.admin.dto.response.VendorListResponse;
import com.example.event_management.common.AppStatus;
import com.example.event_management.common.response.PaginationResponse;
import com.example.event_management.entity.Country;
import com.example.event_management.entity.State;
import com.example.event_management.entity.Vendor;
import com.example.event_management.entity.VendorType;
import com.example.event_management.repository.ICountryRepo;
import com.example.event_management.repository.IStateRepo;
import com.example.event_management.repository.IVendorRepo;
import com.example.event_management.repository.IVendorTypeRepo;

@Service
public class VendorService {
    
    @Autowired
    IVendorRepo iVendorRepo;

     @Autowired
    ICountryRepo icountryRepo;


     @Autowired
    IStateRepo iStateRepo;

       @Autowired
    IVendorTypeRepo iVendorTypeRepo;

    public PaginationResponse<List<VendorListResponse>> getAllVendorWithPagination(int page, int size) {
        Pageable pageable = (Pageable) PageRequest.of(page, size);
        Page<Vendor> userPage = iVendorRepo.findAll(pageable);

        List<VendorListResponse> users = userPage.getContent()
                .stream()
                .map(this::vendorListResponse)
                .toList();

        return new PaginationResponse<>(
                users,
                page,
                size,
                userPage.getTotalElements(),
                userPage.getTotalPages());
    }


    private VendorListResponse vendorListResponse(Vendor vendor) {
        return new VendorListResponse(
                vendor.getId(),
                vendor.getName(),
                vendor.getEmail(),
                vendor.getPhoneNumber(),
                vendor.getCountry().getCountryCode(),
                vendor.getStatus());
    }



    public Boolean createNewVendor(AddUpdateVendorDto addUpdateVendorDto) {
        if (iVendorRepo.existsByEmail(addUpdateVendorDto.getEmail())) {
            throw new IllegalArgumentException("Email is already taken by another vendor");
        }

        if (iVendorRepo.existsByPhoneNumberAndCountry_Id(addUpdateVendorDto.getPhoneNumber(),
                addUpdateVendorDto.getCountryId())) {
            throw new IllegalArgumentException("Phone number with country code another vendor");
        }
        Country country = icountryRepo.findById(addUpdateVendorDto.getCountryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid country selected"));

        State state = iStateRepo.findById(addUpdateVendorDto.getStateId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid State selected"));      
                
        VendorType vendorType = iVendorTypeRepo.findById(addUpdateVendorDto.getVendorTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Vendor Type selected"));                

        Vendor vendor = new Vendor();
        vendor.setName(addUpdateVendorDto.getName());
        vendor.setEmail(addUpdateVendorDto.getEmail());
        vendor.setPhoneNumber(addUpdateVendorDto.getPhoneNumber());
        vendor.setCountry(country); // Correct way to set the entire country entity
        vendor.setStatus(AppStatus.CommonStatus.INACTIVE);
        vendor.setState(state);
        vendor.setVendorTypeId(vendorType);
        vendor.setAddress(addUpdateVendorDto.getAddress());
        iVendorRepo.save(vendor); // Save and get the saved entity with ID
        return Boolean.TRUE; // Convert to UserResponse and return
    }

    public Boolean updateVendor(AddUpdateVendorDto addUpdateVendorDto, Long id) {

        // check emaployee exist or not
        Vendor existingVendor = iVendorRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vendor not found with id: " + id));

        if (iVendorRepo.existsByEmailAndIdNot(addUpdateVendorDto.getEmail(), id)) {
            throw new IllegalArgumentException("Email is already taken by another vendo");
        }

        if (iVendorRepo.existsByPhoneNumberAndCountry_IdAndIdNot(addUpdateVendorDto.getPhoneNumber(),
                addUpdateVendorDto.getCountryId(), id)) {
            throw new IllegalArgumentException("Phone number with country code taken by another user");
        }
        Country country = icountryRepo.findById(addUpdateVendorDto.getCountryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid country selected"));

        State state = iStateRepo.findById(addUpdateVendorDto.getStateId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid State selected"));      
                
        VendorType vendorType = iVendorTypeRepo.findById(addUpdateVendorDto.getVendorTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Vendor Type selected"));   

        existingVendor.setName(addUpdateVendorDto.getName());
        existingVendor.setEmail(addUpdateVendorDto.getEmail());
        existingVendor.setPhoneNumber(addUpdateVendorDto.getPhoneNumber());
        existingVendor.setCountry(country); 
        existingVendor.setState(state);
        existingVendor.setVendorTypeId(vendorType);
        existingVendor.setAddress(addUpdateVendorDto.getAddress());
        iVendorRepo.save(existingVendor); 
        return Boolean.TRUE; // Convert to UserResponse and return
    }

    public Boolean updateStatus(Long id) {

        // check emaployee exist or not
        Vendor existingVendor = iVendorRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with id: " + id));

        if (existingVendor.getStatus() == AppStatus.CommonStatus.ACTIVE) {
            existingVendor.setStatus(AppStatus.CommonStatus.INACTIVE);
        } else {
            existingVendor.setStatus(AppStatus.CommonStatus.ACTIVE);
        }
        iVendorRepo.save(existingVendor); // Save and get the saved entity with ID
        return Boolean.TRUE; // Convert to UserResponse and return
    }

}
