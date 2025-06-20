package com.example.event_management.admin.controller;

import com.example.event_management.admin.dto.EventMemberAccessDto;
import com.example.event_management.admin.dto.response.EventMemberAccessResponse;
import com.example.event_management.admin.service.impl.EventMemberAccessService;
import com.example.event_management.common.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/event-access")
public class EventMemberAccessController {

    @Autowired
    EventMemberAccessService eventMemberAccessService;

    @PostMapping("/create-access")
    public ResponseEntity<ApiResponse<String>> createAccess(@RequestBody EventMemberAccessDto eventMemberAccessDto)
    {
        String memberAccessCreation =  eventMemberAccessService.createEventMemberAccess(eventMemberAccessDto);
        return ApiResponse.success(memberAccessCreation,null);
    }

    @GetMapping("{event_id}/access-list")
    public ResponseEntity<ApiResponse<List<EventMemberAccessResponse>>> accessList(@PathVariable Long event_id)
    {
        List<EventMemberAccessResponse> list =    eventMemberAccessService.getAllMemberAccessList(event_id);
        return ApiResponse.success("access data",list);

    }
}
