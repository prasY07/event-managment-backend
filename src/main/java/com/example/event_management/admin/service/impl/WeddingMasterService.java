package com.example.event_management.admin.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.event_management.admin.dto.CreateUpdateWedEventDto;
import com.example.event_management.admin.dto.response.EventResponse;
import com.example.event_management.admin.dto.response.WeddingMasterResponse;
import com.example.event_management.common.helpers.FileStorageHelper;
import com.example.event_management.common.helpers.event.EventHelper;
import com.example.event_management.common.response.PaginationResponse;
import com.example.event_management.entity.Event;
import com.example.event_management.entity.WeddingMaster;
import com.example.event_management.projection.admin.WeddingListShortProjection;
import com.example.event_management.projection.admin.WeddingSideMasterProjection;
import com.example.event_management.repository.IWeddingMasterRepo;
import com.example.event_management.repository.IWeddingSideMasterRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class WeddingMasterService {

    @Autowired
    IWeddingMasterRepo weddingMasterRepo;

    @Autowired
    IWeddingSideMasterRepo weddingSideMasterRepo;

    public PaginationResponse<List<WeddingListShortProjection>> getAllWedingEvents(int page, int size) {
        Pageable pageable = (Pageable) PageRequest.of(page, size);
        Page<WeddingListShortProjection> wedEventList = weddingMasterRepo.findWeddingShortResponse(pageable);

        return new PaginationResponse<>(
                wedEventList.getContent(),
                page,
                size,
                wedEventList.getTotalElements(),
                wedEventList.getTotalPages());
    }

    public WeddingMasterResponse getSingleInformation(Long id) {
        WeddingMaster wedding = weddingMasterRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid event selected"));

        WeddingMasterResponse response = new WeddingMasterResponse(
                wedding.getId(),
                wedding.getCoupleDisplayName(),
                wedding.getWeddingDate(),
                wedding.getWeddingDate(),
                wedding.getRegistrationEndDate()
        );

        return response;
    }
    // create wedding master
    public WeddingMaster createWeddingMaster(CreateUpdateWedEventDto dto) {

        String weddingId;
        do {
            weddingId = EventHelper.createUniqueEventID();
        } while (weddingMasterRepo.existsByWeddingId(weddingId));

        WeddingMaster wedding = new WeddingMaster();
        wedding.setGroomName(dto.getGroomName());
        wedding.setBrideName(dto.getBrideName());
        wedding.setGroomFatherName(dto.getGroomFatherName());
        wedding.setGroomMotherName(dto.getGroomMotherName());
        wedding.setBrideFatherName(dto.getBrideFatherName());
        wedding.setBrideMotherName(dto.getBrideMotherName());
        wedding.setWeddingDate(dto.getWeddingDate());
        wedding.setRegistrationStartDate(dto.getRegistrationStartDate());
        wedding.setRegistrationEndDate(dto.getRegistrationEndDate());
        wedding.setCoupleDisplayName(dto.getCoupleName());
        wedding.setWeddingId(weddingId);

        return weddingMasterRepo.save(wedding);
    }

    public WeddingMaster updateWeddingMaster(CreateUpdateWedEventDto dto, Long id) {
        WeddingMaster wedding = weddingMasterRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid event selected"));
        wedding.setGroomName(dto.getGroomName());
        wedding.setBrideName(dto.getBrideName());
        wedding.setGroomFatherName(dto.getGroomFatherName());
        wedding.setGroomMotherName(dto.getGroomMotherName());
        wedding.setBrideFatherName(dto.getBrideFatherName());
        wedding.setBrideMotherName(dto.getBrideMotherName());
        wedding.setWeddingDate(dto.getWeddingDate());
        wedding.setRegistrationStartDate(dto.getRegistrationStartDate());
        wedding.setRegistrationEndDate(dto.getRegistrationEndDate());
        wedding.setCoupleDisplayName(dto.getCoupleName());

        return weddingMasterRepo.save(wedding);
    }

   

    // get all wedding side
    public List<WeddingSideMasterProjection> getAllWeddingSides() {
        return weddingSideMasterRepo.findActiveMasterProjection();
    }

        public Boolean uploadCard(Long weddingId, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File must not be empty");
        }

        WeddingMaster wedding = weddingMasterRepo.findById(weddingId)
                .orElseThrow(() -> new EntityNotFoundException("Wedding not found"));

        String oldPath = wedding.getWeddingCard(); // Assuming 'image' stores relative path like
                                           // "uploads/event_8/banner/xyz.png"
        if (oldPath != null) {
            Path oldFilePath = Paths.get(oldPath);
            if (Files.exists(oldFilePath)) {
                try {
                    Files.delete(oldFilePath);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to delete old banner image", e);
                }
            }
        }

        // Save image and get relative path
        String cardPath = FileStorageHelper.saveCardForWedding(file, weddingId, "wedding-card");

        wedding.setWeddingCard(cardPath); // Make sure 'image' field exists in Event entity
        weddingMasterRepo.save(wedding);

        return Boolean.TRUE;
    }
}
