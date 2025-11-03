package com.example.event_management.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.event_management.admin.dto.CreateUpdateWedEventDto;
import com.example.event_management.common.helpers.event.EventHelper;
import com.example.event_management.common.response.PaginationResponse;
import com.example.event_management.entity.WeddingMaster;
import com.example.event_management.projection.admin.WeddingListShortProjection;
import com.example.event_management.repository.IWeddingMasterRepo;

@Service
public class WeddingMasterService {

    @Autowired
    IWeddingMasterRepo weddingMasterRepo;

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
        wedding.setCoupleName(dto.getCoupleName());
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
        wedding.setCoupleName(dto.getCoupleName());

        return weddingMasterRepo.save(wedding);
    }

}
