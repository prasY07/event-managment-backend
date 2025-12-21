package com.example.event_management.web.controllers;

import com.example.event_management.common.response.ApiResponse;
import com.example.event_management.web.dto.response.BulkGuestUploadResponse;
import com.example.event_management.web.dto.response.GuestListResponse;
import com.example.event_management.web.service.impl.WeddingRegistrationService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin/wedding/registration/")
@RequiredArgsConstructor
public class WeddingRegistrationController {

    private final WeddingRegistrationService weddingRegistrationService;

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

    @PostMapping(
            value = "guest/save",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> saveGuest(
            @RequestParam("guestData") String guestData,
            @RequestParam("onwardTicketFile") MultipartFile onwardTicketFile,
            @RequestParam("returnTicketFile") MultipartFile returnTicketFile
    ) throws Exception {

        Long guestId = weddingRegistrationService.saveGuest(
                guestData, onwardTicketFile, returnTicketFile);

        return ApiResponse.success("Guest registered successfully", guestId);


    }

    @GetMapping("/guest/list/{weddingId}")
    public ResponseEntity<ApiResponse<List<GuestListResponse>>> listGuests(
            @PathVariable Long weddingId) {

        List<GuestListResponse> guests =
                weddingRegistrationService.listGuestsByWedding(weddingId);

        if (guests.isEmpty()) {
            return ApiResponse.error("No data available",null, HttpStatus.BAD_REQUEST);
        }

        return ApiResponse.success("Guest list fetched successfully", guests);
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
