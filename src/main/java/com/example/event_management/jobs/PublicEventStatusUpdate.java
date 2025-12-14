package com.example.event_management.jobs;

import com.example.event_management.common.AppStatus;
import com.example.event_management.projection.admin.EventShortProjection;
import com.example.event_management.repository.IEventRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class PublicEventStatusUpdate {

    @Autowired
    IEventRepo eventRepo;

    @Async
    @Scheduled(cron = "0 0 * * * *")
    public void updatePublicEventStatus()
    {

        // List<EventShortProjection> = eventRepo.getEventByEndDate();
    }
}
