package com.example.event_management.admin.controller;

import com.example.event_management.common.response.ApiResponse;
import com.example.event_management.common.response.DashboardCountResponse;
import com.example.event_management.commonapi.service.impl.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/")
@RequiredArgsConstructor
public class DashboardController {

private final DashboardService dashboardService;

    @GetMapping("/counts")
    public ResponseEntity<ApiResponse<DashboardCountResponse>> getCounts() {
        DashboardCountResponse response = dashboardService.getTotalCounts();
        return ApiResponse.success("Dashboard Counts", response);
    }
}
