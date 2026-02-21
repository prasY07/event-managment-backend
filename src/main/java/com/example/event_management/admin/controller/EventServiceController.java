package com.example.event_management.admin.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.event_management.admin.dto.AssignEventServiceDayDto;
import com.example.event_management.admin.dto.response.EventDayResponse;
import com.example.event_management.admin.dto.response.EventServiceResponse;
import com.example.event_management.admin.service.impl.EventDayService;
import com.example.event_management.common.response.ApiResponse;

@RestController
@RequestMapping("/api/admin/event-service")
public class EventServiceController {

    @Autowired
    EventDayService eventDayService;

    @GetMapping("{eventId}/service-list")
    public ResponseEntity<ApiResponse<List<EventServiceResponse>>> allEventServices(@PathVariable Long eventId) {
        List<EventServiceResponse> res = eventDayService.getAllEventService(eventId);
        return ApiResponse.success("Service List", res);

    }

    @GetMapping("{eventId}/day-list")
    public ResponseEntity<ApiResponse<List<EventDayResponse>>> allEventDays(@PathVariable Long eventId) {
        List<EventDayResponse> res = eventDayService.getAllEventDay(eventId);
        return ApiResponse.success("Event Day List", res);

    }

    @PostMapping("/assign-day-service-to-member")
    public ResponseEntity<ApiResponse<String>> assignService(@RequestBody AssignEventServiceDayDto assignEventServiceDayDto)
    {

        boolean res = eventDayService.assignServiceDaysToMember(assignEventServiceDayDto);

        return ApiResponse.success("Data Saved Successfully",null);
    }


}
