package com.example.event_management.commonapi.service.impl;

import com.example.event_management.commonapi.dto.response.CountryResponse;
import com.example.event_management.entity.Country;
import com.example.event_management.repository.ICountryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    @Autowired
    ICountryRepo iCountryRepo;

    public List<CountryResponse> getAllCountry() {
        List<Country> countries = iCountryRepo.findAll();
        return countries.stream().map(this::createResponse).toList();
    }

    private CountryResponse createResponse(Country country) {
        return new CountryResponse(
                country.getId(),
                country.getName(),
                country.getCountryCode());
    }
}
