package com.example.event_management.commonapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.event_management.common.response.ApiResponse;
import com.example.event_management.commonapi.dto.response.CountryResponse;
import com.example.event_management.commonapi.service.impl.CountryService;

@RestController
@RequestMapping("/api/common/country")
public class CountryController {
    @Autowired
    CountryService countryService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<CountryResponse>>> allCountryList() {
        List<CountryResponse> allCountryList = countryService.getAllCountry();
        return ApiResponse.success("Country list", allCountryList);
    }
}
