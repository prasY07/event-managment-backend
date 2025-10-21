package com.example.event_management.web.controllers;

import com.example.event_management.admin.dto.EventRegistrationWithoutQRDto;
import com.example.event_management.admin.dto.response.EventRegistrationWithoutQRResponse;
import com.example.event_management.common.helpers.dto.request.ResendOtpDto;
import com.example.event_management.common.helpers.dto.request.SendOtpDto;
import com.example.event_management.common.helpers.dto.request.VerifyOtpDto;
import com.example.event_management.common.helpers.event.EventOtpHelper;
import com.example.event_management.common.helpers.event.EventRegistrationHelper;
import com.example.event_management.common.response.SendOtpResponse;
import com.example.event_management.entity.BusinessDetails;
import com.example.event_management.repository.IBusinessDetails;
import com.example.event_management.web.dto.BusinessDetailsDTO;
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

    @Autowired
    private IBusinessDetails iBusinessDetails;



    @GetMapping("{eventId}/information")
    public ResponseEntity<ApiResponse<WebEventShortResponse>> getEventInfo(@PathVariable String eventId)
    {
        WebEventShortResponse res = webEventService.getInfo(eventId);
        return ApiResponse.success("Event Information",res);
    }

    @PostMapping("/sendOtp")
    public ResponseEntity<ApiResponse<SendOtpResponse>> sendOtp(@Valid @RequestBody SendOtpDto request ) {
        SendOtpResponse response = eventOtpHelper.sendOtp(request);
        return ApiResponse.success("Response from send OTP API", response);
    }

    @PostMapping("/resendOtp")
    public ResponseEntity<ApiResponse<SendOtpResponse>> resendOtp(@RequestBody ResendOtpDto resendOtpDto) {
        SendOtpResponse response = eventOtpHelper.resendOtp(resendOtpDto);
        return ApiResponse.success("Response from resend OTP API", response);
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

    @PostMapping("/verifyOtp")
    public ResponseEntity<ApiResponse<String>> verifyOtp(@RequestBody VerifyOtpDto verifyOtpDto) {
        String userOtpId = eventOtpHelper.verifyOtp(verifyOtpDto);
        return ApiResponse.success("OTP verified successfully",userOtpId);
    }

    @PostMapping("business-registration")
    public ResponseEntity<ApiResponse<String>> companyDetails(@RequestBody BusinessDetailsDTO businessDetailsDTO) {
        BusinessDetails businessDetails = new BusinessDetails();
        businessDetails.setFname(businessDetailsDTO.getFname());
        businessDetails.setLname(businessDetailsDTO.getLname());
        businessDetails.setGender(BusinessDetails.Gender.valueOf(businessDetailsDTO.getGender())); // Enum
        businessDetails.setPhoneNumber(businessDetailsDTO.getPhoneNumber());
        businessDetails.setEmail(businessDetailsDTO.getEmail());
        businessDetails.setCompanyName(businessDetailsDTO.getCompanyName());
        businessDetails.setCompanyAddress(businessDetailsDTO.getCompanyAddress());
        businessDetails.setBusinessSummary(businessDetailsDTO.getBusinessSummary());
        businessDetails.setEemaMember(BusinessDetails.EemaMember.valueOf(businessDetailsDTO.getEemaMember())); // Enum
        businessDetails.setCategories(businessDetailsDTO.getCategories()); // comma-separated string
        businessDetails.setEmaa(BusinessDetails.Emaa.valueOf(businessDetailsDTO.getEmaa()));
        iBusinessDetails.save(businessDetails);
        return ApiResponse.success("Registration Sucesfully",null);

    }
}
