package com.example.event_management.web.controllers;

import com.example.event_management.admin.dto.EventRegistrationWithoutQRDto;
import com.example.event_management.admin.dto.response.EventMemberTypeResponse;
import com.example.event_management.admin.dto.response.EventRegistrationWithoutQRResponse;
import com.example.event_management.common.helpers.dto.request.ResendOtpDto;
import com.example.event_management.common.helpers.dto.request.SendOtpDto;
import com.example.event_management.common.helpers.event.EventOtpHelper;
import com.example.event_management.common.helpers.event.EventRegistrationHelper;
import com.example.event_management.web.dto.response.WebEventMemberListResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.event_management.common.response.ApiResponse;
import com.example.event_management.web.dto.response.WebEventShortResponse;
import com.example.event_management.web.service.impl.WebEventService;

import java.util.List;


@RestController
@RequestMapping("/api/web/event/")
@RequiredArgsConstructor
public class WebEventController {

    private final WebEventService webEventService;

    private final EventOtpHelper eventOtpHelper;

    private final EventRegistrationHelper  eventRegistrationHelper;



    @GetMapping("{eventId}/information")
    public ResponseEntity<ApiResponse<WebEventShortResponse>> getEventInfo(@PathVariable String eventId)
    {
        WebEventShortResponse res = webEventService.getInfo(eventId);
        return ApiResponse.success("Event Information",res);
    }

    @PostMapping("/sendOtp")
    public ResponseEntity<ApiResponse<String>> sendOtp(@Valid @RequestBody SendOtpDto request ) {
      return this.eventOtpHelper.sendOtp(request);
    }

    @PostMapping("/resendOtp")
    public ResponseEntity<ApiResponse<String>> resendOtp(@Valid @RequestBody ResendOtpDto request ) {
        return this.eventOtpHelper.resendOtp(request);
    }

    @GetMapping("{eventId}/member-list")
    public ResponseEntity<ApiResponse<List<WebEventMemberListResponse>>> eventMemberType(@PathVariable String eventId)
    {
        List<WebEventMemberListResponse> memberType = webEventService.eventAllMembers(eventId);
        return ApiResponse.success("Event All Members", memberType);
    }

    @PostMapping("/user-registration")
    public ResponseEntity<ApiResponse<String>> newUserRegister(@Valid @RequestBody EventRegistrationWithoutQRDto dto ) {
         EventRegistrationWithoutQRResponse res =  this.eventRegistrationHelper.newRegistration(dto);
        return ApiResponse.success("Registration Successfully", null);


    }
}
