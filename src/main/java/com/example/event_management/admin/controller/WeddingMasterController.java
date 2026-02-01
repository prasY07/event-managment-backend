package com.example.event_management.admin.controller;

import java.util.List;

import com.example.event_management.web.dto.response.BulkGuestUploadResponse;
import com.example.event_management.web.service.impl.WeddingRegistrationService;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

import com.example.event_management.admin.dto.CreateUpdateWedEventDto;
import com.example.event_management.admin.dto.response.WeddingMasterResponse;
import com.example.event_management.admin.service.impl.WeddingMasterService;
import com.example.event_management.common.response.ApiResponse;
import com.example.event_management.common.response.PaginationResponse;
import com.example.event_management.entity.WeddingMaster;
import com.example.event_management.projection.admin.WeddingListShortProjection;
import com.example.event_management.projection.admin.WeddingSideMasterProjection;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin/wedding")
public class WeddingMasterController {

    @Autowired
    WeddingMasterService weddingMasterService;

    @Autowired
    WeddingRegistrationService weddingRegistrationService;


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

    


        @PostMapping(path = "{id}/upload-card", consumes = {"multipart/form-data"})
    public ResponseEntity<ApiResponse<String>> uploadBanner(
            @PathVariable Long id,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        Boolean cardSave = weddingMasterService.uploadCard(id, file);
        return ApiResponse.success("Card Upload successfully", null);
    }


    @PostMapping("guest/bulk-upload/{weddingId}")
    public ResponseEntity<ApiResponse<BulkGuestUploadResponse>> bulkUpload(
            @PathVariable Long weddingId,
            @RequestParam MultipartFile file
    ) {

        int uploadedCount = weddingRegistrationService.bulkUploadGuests(weddingId, file);
        int totalRows = getExcelRowCount(file);
        int skipped = totalRows - uploadedCount;
        BulkGuestUploadResponse response = new BulkGuestUploadResponse(uploadedCount, skipped, null);
        return ApiResponse.success("Guests uploaded successfully", response);
    }

    private int getExcelRowCount(MultipartFile file) {
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {

            Sheet sheet = workbook.getSheetAt(0);

            // total physical rows including header
            int totalRows = sheet.getPhysicalNumberOfRows();

            // subtract header row
            return Math.max(totalRows - 1, 0);

        } catch (Exception e) {
            throw new RuntimeException("Failed to read Excel file", e);
        }
    }

}
