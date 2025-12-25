package com.example.event_management.jobs;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.event_management.entity.WeddingFunctionNotification;
import com.example.event_management.repository.IWeddingFunctionNotificationRepository;
import com.example.event_management.web.repo.IWeddingGuestUploadRepo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class WeddingReminderUpdate {

    @Autowired
    IWeddingFunctionNotificationRepository weddingFunctionNotificationRepository;

    @Autowired
    IWeddingGuestUploadRepo iWeddingGuestUploadRepo;

    @Scheduled(cron = "0 0 * * * *") // every hour
    public void updateWeddingReminders() {

        // Implementation for updating wedding reminders
        log.info("WeddingReminderJob running...");
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        log.info("WeddingReminderJob executed at date: {} and time: {}", currentDate, currentTime);
        // Add your logic to update wedding reminders here

        // List<WeddingFunctionNotification> notificationsToUpdate = weddingFunctionNotificationRepository
        //         .findNotificationsToUpdate(currentDate, currentTime);

        // for (WeddingFunctionNotification notification : notificationsToUpdate) {
        //     Integer sideId = notification.getWeddingFunction().getSide().getSideId();
        //     log.info("Updating notification id: {} for sideId: {}", notification.getId(), sideId);

        //     Long weddingId = notification.getWeddingMaster().getId();

        //     // check guest exits for wedding id and side id
        //     boolean guestExists = iWeddingGuestUploadRepo.existsByWeddingMaster_IdAndSide_SideId(
        //             weddingId,
        //             sideId);

        //     // 🔹 If guest does NOT exist → skip this notification
        //     if (!guestExists) {
        //         log.info("No guest found for weddingId: {}, sideId: {}. Skipping.",
        //                 weddingId, sideId);
        //         continue; // ⬅️ THIS LINE
        //     }

            

        // }

    }

}
