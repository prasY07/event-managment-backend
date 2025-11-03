package com.example.event_management.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.event_management.admin.dto.CreateUpdateWedEventDto;
import com.example.event_management.admin.service.impl.WeddingMasterService;
import com.example.event_management.common.response.ApiResponse;
import com.example.event_management.entity.WeddingMaster;

@RequestMapping("/api/admin/wedding")
public class WeddingMasterController {

    @Autowired
    WeddingMasterService weddingMasterService;

   @PostMapping("/create")
   public ResponseEntity<ApiResponse<String>> createWeddingEvent(CreateUpdateWedEventDto dto ) {
        WeddingMaster savedWedding = weddingMasterService.createWeddingMaster(dto);
        return ApiResponse.success("Wedding Event create Successfully",null);  
     }


       @PostMapping("/{id}/update")
   public ResponseEntity<ApiResponse<String>> updateWeddingEvent(CreateUpdateWedEventDto dto  , @RequestParam Long id) {
        WeddingMaster updateWedding = weddingMasterService.updateWeddingMaster(dto , id);
        return ApiResponse.success("Wedding Event create Successfully",null);  
     }


    
}
