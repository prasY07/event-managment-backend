package com.example.event_management.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.event_management.admin.dto.CreateUpdateWedEventDto;
import com.example.event_management.admin.dto.response.EventShortResponse;
import com.example.event_management.admin.dto.response.WeddingMasterResponse;
import com.example.event_management.admin.service.impl.WeddingMasterService;
import com.example.event_management.common.response.ApiResponse;
import com.example.event_management.common.response.PaginationResponse;
import com.example.event_management.entity.WeddingMaster;
import com.example.event_management.projection.admin.WeddingListShortProjection;
import com.example.event_management.projection.admin.WeddingSideMasterProjection;

@RestController
@RequestMapping("/api/admin/wedding")
public class WeddingMasterController {

    @Autowired
    WeddingMasterService weddingMasterService;

     @GetMapping("/list")
    public ResponseEntity<ApiResponse<PaginationResponse<List<WeddingListShortProjection>>>> allWedingEventWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PaginationResponse<List<WeddingListShortProjection>> paginatedUsers = weddingMasterService.getAllWedingEvents(page, size);
        return ApiResponse.successWithPagination(
                "Wedding List",
                paginatedUsers.getItems(),
                paginatedUsers.getPage(),
                paginatedUsers.getSize(),
                paginatedUsers.getTotalElements(),
                paginatedUsers.getTotalPages());
    }

   @PostMapping("/create")
   public ResponseEntity<ApiResponse<String>> createWeddingEvent(@RequestBody CreateUpdateWedEventDto dto ) {
        WeddingMaster savedWedding = weddingMasterService.createWeddingMaster(dto);
        return ApiResponse.success("Wedding Event create Successfully",null);  
     }


    @PostMapping("/{id}/update")
   public ResponseEntity<ApiResponse<String>> updateWeddingEvent(@RequestBody CreateUpdateWedEventDto dto  , @RequestParam Long id) {
        WeddingMaster updateWedding = weddingMasterService.updateWeddingMaster(dto , id);
        return ApiResponse.success("Wedding Event updated Successfully",null);
     }

       @GetMapping("{id}/wedding-information")
    public ResponseEntity<ApiResponse<WeddingMasterResponse>> getInfor(@PathVariable Long id) {
        WeddingMasterResponse eventInfo = weddingMasterService.getSingleInformation(id);
        return ApiResponse.success("Event Information", eventInfo);
    }

    
    // get all wedding side
    @GetMapping("/sides")
    public ResponseEntity<ApiResponse<List<WeddingSideMasterProjection>>> getAllWeddingSides() {
        List<WeddingSideMasterProjection> sides = weddingMasterService.getAllWeddingSides();
        return ApiResponse.success("Wedding Sides", sides);
    }

    


    
}
