package com.example.event_management.admin.controller;

import com.example.event_management.admin.dto.NotificationRequest;
import com.example.event_management.admin.dto.response.NotificationResponse;
import com.example.event_management.admin.service.impl.WeddingFunctionNotificationService;
import com.example.event_management.common.AppStatus;
import com.example.event_management.common.response.ApiResponse;
import com.example.event_management.common.response.PaginationResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/wedding/function/notification")
public class WeddingFunctionNotificationController {

    private WeddingFunctionNotificationService weddingFunctionNotificationService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Long>> addFunction(
            @Valid @RequestBody NotificationRequest request) {

        Long notificationId = weddingFunctionNotificationService.createNotification(request);
        return ApiResponse.success("Wedding notification created Successfully", notificationId);
    }

    @PutMapping("/{notificationId}/update")
    public ResponseEntity<ApiResponse<String>> updateNotification(
            @PathVariable Long notificationId,
            @Valid @RequestBody NotificationRequest request) {

        weddingFunctionNotificationService.updateNotification(notificationId, request);
        return ApiResponse.success("Wedding notification updated Successfully", null);

    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<ApiResponse<Long>> deleteNotification(
            @PathVariable Long notificationId) {

        weddingFunctionNotificationService.deleteNotification(notificationId);

        return ApiResponse.success("Wedding notification event deleted Successfully for notification Id : "+notificationId, null);

    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<PaginationResponse<List<NotificationResponse>>>> listNotifications(
            @RequestParam Long weddingId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PaginationResponse<List<NotificationResponse>> paginatedNotifications =
                weddingFunctionNotificationService.listOfNotificationsByWedding(weddingId, page, size);

        return ApiResponse.successWithPagination(
                "Wedding Function Notifications List",
                paginatedNotifications.getItems(),
                paginatedNotifications.getPage(),
                paginatedNotifications.getSize(),
                paginatedNotifications.getTotalElements(),
                paginatedNotifications.getTotalPages()
        );
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Void>> updateStatus(
            @PathVariable Long id,
            @RequestParam AppStatus.notificationStatus status) {

        weddingFunctionNotificationService.updateStatus(id, status);

        return ApiResponse.success(
                "Notification status updated successfully",
                null
        );
    }

}
