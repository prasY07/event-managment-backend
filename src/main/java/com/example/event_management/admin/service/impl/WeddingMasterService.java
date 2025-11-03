package com.example.event_management.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.event_management.admin.dto.CreateUpdateWedEventDto;
import com.example.event_management.common.helpers.event.EventHelper;
import com.example.event_management.common.helpers.wedding.WeddingHelper;
import com.example.event_management.entity.WeddingMaster;
import com.example.event_management.repository.IWeddingMasterRepo;

@Service
public class WeddingMasterService {

    @Autowired
    IWeddingMasterRepo weddingMasterRepo;


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
        wedding.setWeddingId(weddingId);

        return weddingMasterRepo.save(wedding);
    }


           public WeddingMaster updateWeddingMaster(CreateUpdateWedEventDto dto , Long id) {
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

        return weddingMasterRepo.save(wedding);
    }


   
}
