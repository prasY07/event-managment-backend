package com.example.event_management.admin.controller;

import com.example.event_management.admin.dto.EventRegistrationWithoutQRDto;
import com.example.event_management.admin.dto.response.EventRegistrationWithoutQRResponse;
import com.example.event_management.admin.service.impl.EventRegistrationService;
import com.example.event_management.common.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/event-register")
public class EventRegistrationController {

    @Autowired
    EventRegistrationService eventRegistrationService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<EventRegistrationWithoutQRResponse>> create(@RequestBody EventRegistrationWithoutQRDto eventRegistrationWithoutQRDto)
    {
        EventRegistrationWithoutQRResponse eventRegistrationWithoutQRResponse =  eventRegistrationService.newRegistration(eventRegistrationWithoutQRDto);
        return ApiResponse.success("Registration Success",eventRegistrationWithoutQRResponse);
    }
}
