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
@RequestMapping("/api/wedding/registration/")
@RequiredArgsConstructor
public class WeddingRegistrationController {

    private final WeddingRegistrationService weddingRegistrationService;



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




}
