package com.example.event_management.commonapi.service.impl;

import com.example.event_management.common.response.DashboardCountResponse;
import com.example.event_management.repository.IEventRepo;
import com.example.event_management.repository.IUserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DashboardService {

    private final IEventRepo eventRepo;
    private final IUserRepo userRepo;

    public DashboardCountResponse getTotalCounts() {
        log.info("Inside Dashboard Service");
        Long totalEvents = eventRepo.count();
        Long totalUsers = userRepo.count();
        log.info("Total Events: {}, Total Users: {}", totalEvents, totalUsers);
        return new DashboardCountResponse(totalEvents, totalUsers);
    }

}
