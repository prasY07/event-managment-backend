package com.example.event_management.admin.controller;


import com.example.event_management.admin.dto.AssignEventServiceDayDto;
import com.example.event_management.admin.dto.response.EventRegistrationWithoutQRResponse;
import com.example.event_management.admin.dto.response.EventServiceResponse;
import com.example.event_management.admin.service.impl.EventDayService;
import com.example.event_management.common.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/event-service")
public class EventServiceController {

    @Autowired
    EventDayService eventDayService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<EventServiceResponse>>> allEventServices() {
        List<EventServiceResponse> res = eventDayService.getAllEventService();
        return ApiResponse.success("Service List", res);

    }

    @PostMapping("/assign-day-service-to-member")
    ResponseEntity<ApiResponse<String>> assignService(@RequestBody AssignEventServiceDayDto assignEventServiceDayDto)
    {
        boolean res = eventDayService.assignServiceDaysToMember(assignEventServiceDayDto);

        return ApiResponse.success("Data Saved Successfully",null);
    }


}
