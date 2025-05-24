package com.example.event_managment.admin.controller;

import com.example.event_managment.admin.dto.EventDto;
import com.example.event_managment.admin.dto.EventUpdateStatusRequest;
import com.example.event_managment.admin.dto.response.EventResponse;
import com.example.event_managment.admin.service.impl.EventService;
import com.example.event_managment.common.response.ApiResponse;
import com.example.event_managment.common.response.PaginationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<PaginationResponse<List<EventResponse>>>> allUsersWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1") int size
    )
    {
        PaginationResponse<List<EventResponse>> paginatedUsers = eventService.getAllEvents(page, size);
        return ApiResponse.successWithPagination(
                "Events List",
                paginatedUsers.getItems(),
                paginatedUsers.getPage(),
                paginatedUsers.getSize(),
                paginatedUsers.getTotalElements(),
                paginatedUsers.getTotalPages()
        );
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<EventResponse>> createEvent(
            @RequestBody EventDto eventDto
    )  {
        EventResponse newEvent = eventService.createNewEvent(eventDto);
        return ApiResponse.success("Event added successfully", newEvent);
    }


    @PutMapping("{id}/update-status")
    public ResponseEntity<ApiResponse<EventResponse>> updateStatusEvent(@PathVariable Long id, @RequestBody EventUpdateStatusRequest request)
    {
        EventResponse newEvent = eventService.updateEventStatus(id, request);
        return  ApiResponse.success("event status update successfully",newEvent);
    }

    @PutMapping("{id}/update-event")
    public ResponseEntity<ApiResponse<EventResponse>> updateEvent(@PathVariable Long id, @RequestBody EventDto eventDto)
    {
        EventResponse newEvent = eventService.updateEvent(id, eventDto);
        return  ApiResponse.success("event update successfully",newEvent);
    }

    @PostMapping(
            path = "{id}/upload-banner"
    )
    public ResponseEntity<ApiResponse<EventResponse>> uploadBanner(
            @PathVariable Long id,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
//        System.out.println("Received file: " + (file != null ? file.getOriginalFilename() : "null"));
//        System.out.println("File size: " + (file != null ? file.getSize() : "null"));

        EventResponse newEvent = eventService.uploadBanner(id, file);
        return ApiResponse.success("Event banner uploaded successfully", newEvent);
    }

    @GetMapping("{id}/event-information")
    public ResponseEntity<ApiResponse<EventResponse>> eventInfo(@PathVariable Long id)
    {
        EventResponse eventInfo = eventService.eventInfo(id);
        return ApiResponse.success("Event Information", eventInfo);
    }

    @GetMapping("{eventId}/event-info")
    public ResponseEntity<ApiResponse<EventResponse>> eventInfoWithUniqueId(@PathVariable String eventId)
    {
        EventResponse eventInfo = eventService.eventInfoWithEventUniqueId(eventId);
        return ApiResponse.success("Event Information", eventInfo);
    }




}
