package com.example.event_management.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.event_management.common.response.ApiResponse;
import com.example.event_management.web.dto.response.WebEventShortResponse;
import com.example.event_management.web.service.impl.WebEventService;


@RestController
@RequestMapping("/api/web/event/")
public class WebEventController {

    @Autowired
    WebEventService webEventService;

    @GetMapping("{eventId}/information")
    public ResponseEntity<ApiResponse<WebEventShortResponse>> getEventInfo(@PathVariable String eventId)
    {
        WebEventShortResponse res = webEventService.getInfo(eventId);
        return ApiResponse.success("Event Information",res);
    }
    
}
