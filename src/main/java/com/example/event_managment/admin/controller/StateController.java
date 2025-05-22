package com.example.event_managment.admin.controller;

import com.example.event_managment.admin.dto.response.StateShortResponse;
import com.example.event_managment.admin.service.impl.StateService;
import com.example.event_managment.common.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/state")
public class StateController {

    @Autowired
    StateService stateService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<StateShortResponse>>> allStates()
    {
        List<StateShortResponse> allStates = stateService.getAllStates();
        return ApiResponse.success("States list", allStates);
    }
}
