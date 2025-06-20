package com.example.event_management.admin.controller;

import com.example.event_management.admin.dto.response.EventAccessTypeResponse;
import com.example.event_management.admin.service.impl.EventAccessService;
import com.example.event_management.common.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/event-access")
public class EventAccessTypeController {

    @Autowired
    EventAccessService eventAccessService;

    @GetMapping("{eventId}/list")
    public ResponseEntity<ApiResponse<List<EventAccessTypeResponse>>> eventMemberType(@PathVariable Long eventId)
    {
        List<EventAccessTypeResponse> memberType = eventAccessService.eventAllAccess(eventId);
        return ApiResponse.success("Event Access", memberType);
    }

    @DeleteMapping("{accessTypeId}/delete-access-type")
    public ResponseEntity<ApiResponse<String>> deleteMember(@PathVariable Long accessTypeId)
    {
        String removeAccessType =  eventAccessService.deleteAccessTypeData(accessTypeId);
        return ApiResponse.success(removeAccessType, null);
    }
}
