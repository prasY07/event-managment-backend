package com.example.event_management.web.controllers;

import com.example.event_management.common.response.ApiResponse;
import com.example.event_management.web.dto.response.WebEventShortResponse;
import com.example.event_management.web.service.impl.WebEventService;
import com.example.event_management.web.service.impl.WebWeddingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/web/wedding/")
public class WebWeddingController {

    @Autowired
    WebWeddingService webWeddingService;

    @GetMapping("/{id}/card")
    public ResponseEntity<ApiResponse<String>>  getWeddingCard(@PathVariable String id)
    {
        String cardUrl = webWeddingService.getWeddingCard(id);
        return  ApiResponse.success("Wedding Card" , cardUrl);
    }
}
