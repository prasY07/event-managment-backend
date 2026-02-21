package com.example.event_management.commonapi.controller;

import com.example.event_management.common.response.ApiResponse;
import com.example.event_management.commonapi.dto.response.StateShortResponse;
import com.example.event_management.commonapi.service.impl.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/common/state")
public class StateController {
    @Autowired
    StateService stateService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<StateShortResponse>>> allStates() {
        List<StateShortResponse> allStates = stateService.getAllStates();
        return ApiResponse.success("States list", allStates);
    }
}
