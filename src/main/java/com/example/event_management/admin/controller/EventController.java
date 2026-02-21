package com.example.event_management.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.event_management.admin.dto.CreateEventDto;
import com.example.event_management.admin.dto.EventDto;
import com.example.event_management.admin.dto.response.EventResponse;
import com.example.event_management.admin.dto.response.EventShortResponse;
import com.example.event_management.admin.service.impl.EventService;
import com.example.event_management.common.response.ApiResponse;
import com.example.event_management.common.response.PaginationResponse;

@RestController("adminEventController")
@RequestMapping("/api/admin/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<PaginationResponse<List<EventResponse>>>> allEventsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PaginationResponse<List<EventResponse>> paginatedUsers = eventService.getAllEvents(page, size);
        return ApiResponse.successWithPagination(
                "Events List",
                paginatedUsers.getItems(),
                paginatedUsers.getPage(),
                paginatedUsers.getSize(),
                paginatedUsers.getTotalElements(),
                paginatedUsers.getTotalPages());
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<EventResponse>> createEvent(
            @RequestBody CreateEventDto eventDto) {
        EventResponse newEvent = eventService.createNewEvent(eventDto);
        return ApiResponse.success("Event added successfully", newEvent);
    }

    @PutMapping("{id}/update-event")
    public ResponseEntity<ApiResponse<EventResponse>> updateEvent(@PathVariable Long id,
            @RequestBody EventDto eventDto) {
        EventResponse newEvent = eventService.updateEvent(id, eventDto);
        return ApiResponse.success("event update successfully", newEvent);
    }

    @PostMapping(path = "{id}/upload-banner")
    public ResponseEntity<ApiResponse<EventResponse>> uploadBanner(
            @PathVariable Long id,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        EventResponse newEvent = eventService.uploadBanner(id, file);
        return ApiResponse.success("Event banner uploaded successfully", newEvent);
    }

    @GetMapping("{id}/event-information")
    public ResponseEntity<ApiResponse<EventShortResponse>> eventInfo(@PathVariable Long id) {
        EventShortResponse eventInfo = eventService.eventInfo(id);
        return ApiResponse.success("Event Information", eventInfo);
    }

    @GetMapping("{eventId}/event-info")
    public ResponseEntity<ApiResponse<EventResponse>> eventInfoWithUniqueId(@PathVariable String eventId) {
        EventResponse eventInfo = eventService.eventInfoWithEventUniqueId(eventId);
        return ApiResponse.success("Event Information", eventInfo);
    }

    @PatchMapping("{eventId}/update-status")
    public ResponseEntity<ApiResponse<EventResponse>> updateStatus(@PathVariable Long eventId) {
        EventResponse newEvent = eventService.updateStatus(eventId);
        return ApiResponse.success("Status Updated successfully", newEvent);
    }

}
