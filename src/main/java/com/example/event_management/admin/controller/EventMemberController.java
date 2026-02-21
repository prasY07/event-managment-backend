package com.example.event_management.admin.controller;

import com.example.event_management.admin.dto.EventFeesDto;
import com.example.event_management.admin.dto.response.EventMemberTypeResponse;
import com.example.event_management.admin.service.impl.EventMemberService;
import com.example.event_management.common.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/event-member")
public class EventMemberController {

    @Autowired
    EventMemberService eventMemberService;


    @GetMapping("{eventId}/list")
    public ResponseEntity<ApiResponse<List<EventMemberTypeResponse>>> eventMemberType(@PathVariable Long eventId)
    {
        List<EventMemberTypeResponse> memberType = eventMemberService.eventAllMembers(eventId);
        return ApiResponse.success("Event All Members", memberType);
    }

    @DeleteMapping("{memberId}/delete-member")
    public ResponseEntity<ApiResponse<String>> deleteMember(@PathVariable Long memberId)
    {
       String removeMemberType =  eventMemberService.deleteMemberData(memberId);
       return ApiResponse.success(removeMemberType, null);
    }


    @PutMapping("{eventId}/update-fees")
    public ResponseEntity<ApiResponse<String>> updateFees(@PathVariable Long eventId, @RequestBody EventFeesDto eventFeesDto)
    {
        String updateFees =  eventMemberService.updateEventFees(eventId,eventFeesDto);
        return ApiResponse.success(updateFees, null);
    }
}
