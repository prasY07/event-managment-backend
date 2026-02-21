package com.example.event_management.admin.controller;

import com.example.event_management.admin.dto.EventRegistrationWithoutQRDto;
import com.example.event_management.admin.dto.response.EventRegisterUserShortResponse;
import com.example.event_management.admin.dto.response.EventRegistrationWithoutQRResponse;
import com.example.event_management.admin.service.impl.EventRegistrationService;
import com.example.event_management.common.response.ApiResponse;
import com.example.event_management.common.response.PaginationResponse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/event-register")
public class EventRegistrationController {

    @Autowired
    EventRegistrationService eventRegistrationService;


     @GetMapping("{eventId}/list")
    public ResponseEntity<ApiResponse<PaginationResponse<List<EventRegisterUserShortResponse>>>> allUsersWithPagination(
             @PathVariable Long eventId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
           
            ) {
        PaginationResponse<List<EventRegisterUserShortResponse>> paginatedEventRegisterUser = eventRegistrationService.getAllEventUser(eventId,page, size);
        return ApiResponse.successWithPagination(
                "Events List",
                paginatedEventRegisterUser.getItems(),
                paginatedEventRegisterUser.getPage(),
                paginatedEventRegisterUser.getSize(),
                paginatedEventRegisterUser.getTotalElements(),
                paginatedEventRegisterUser.getTotalPages());
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<EventRegistrationWithoutQRResponse>> create(@RequestBody EventRegistrationWithoutQRDto eventRegistrationWithoutQRDto)
    {
        EventRegistrationWithoutQRResponse eventRegistrationWithoutQRResponse =  eventRegistrationService.newRegistration(eventRegistrationWithoutQRDto);
        return ApiResponse.success("Registration Success",eventRegistrationWithoutQRResponse);
    }
}
