package com.example.event_management.admin.service.impl;

import com.example.event_management.admin.dto.NotificationRequest;
import com.example.event_management.admin.dto.response.NotificationResponse;
import com.example.event_management.common.AppStatus;
import com.example.event_management.common.response.PaginationResponse;
import com.example.event_management.entity.WeddingFunction;
import com.example.event_management.entity.WeddingFunctionNotification;
import com.example.event_management.entity.WeddingMaster;
import com.example.event_management.repository.IWeddingFunctionNotificationRepository;
import com.example.event_management.repository.IWeddingFunctionRepo;
import com.example.event_management.repository.IWeddingMasterRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WeddingFunctionNotificationService {

    private final IWeddingFunctionNotificationRepository weddingFunctionNotificationRepository;
    private final IWeddingMasterRepo weddingMasterRepo;
    private final IWeddingFunctionRepo weddingFunctionRepo;

    public Long createNotification(NotificationRequest req) {

        WeddingMaster wedding = weddingMasterRepo.findById(req.getWeddingId())
                .orElseThrow(() -> new RuntimeException("Wedding not found"));
        WeddingFunction function = weddingFunctionRepo.findById(req.getWeddingFunctionId())
                .orElseThrow(() -> new RuntimeException("Wedding function not found"));


        WeddingFunctionNotification notification =
                WeddingFunctionNotification.builder()
                        .weddingMaster(wedding)
                        .weddingFunction(function)
                        .title(req.getTitle())
                        .message(req.getMessage())
                        .notificationDate(req.getNotificationDate())
                        .notificationTime(req.getNotificationTime())
                        .status(AppStatus.notificationStatus.PENDING)
                        .deleted(false)
                        .build();

        return weddingFunctionNotificationRepository.save(notification).getId();
    }


    public void updateNotification(Long id, NotificationRequest req) {

        WeddingFunctionNotification notification = weddingFunctionNotificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        notification.setTitle(req.getTitle());
        notification.setMessage(req.getMessage());
        notification.setNotificationDate(req.getNotificationDate());
        notification.setNotificationTime(req.getNotificationTime());
    }


    @Transactional(readOnly = true)
    public PaginationResponse<List<NotificationResponse>> listOfNotificationsByWedding(
            Long weddingId,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<WeddingFunctionNotification> notificationPage =
                weddingFunctionNotificationRepository
                        .findByWeddingMaster_IdAndDeletedFalse(weddingId, pageable);

        List<NotificationResponse> notifications =
                notificationPage.getContent()
                        .stream()
                        .map(n -> new NotificationResponse(
                                n.getId(),
                                n.getTitle(),
                                n.getMessage(),
                                n.getNotificationDate(),
                                n.getNotificationTime(),
                                n.getStatus()))
                        .toList();

        return new PaginationResponse<>(
                notifications,
                notificationPage.getNumber(),
                notificationPage.getSize(),
                notificationPage.getTotalElements(),
                notificationPage.getTotalPages()
        );
    }


    public void deleteNotification(Long id) {
        WeddingFunctionNotification notification = weddingFunctionNotificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setDeleted(true);
    }

    public void updateStatus(Long id, AppStatus.notificationStatus status) {
        WeddingFunctionNotification notification = weddingFunctionNotificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setStatus(status);
    }

}
