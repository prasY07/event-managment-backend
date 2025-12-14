package com.example.event_management.entity;

import com.example.event_management.common.AppStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "wedding_function_notification")
@EntityListeners(AuditingEntityListener.class)
@Builder
@Data
public class WeddingFunctionNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wedding_id", nullable = false)
    private WeddingMaster weddingMaster;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wedding_function_id", nullable = false)
    private WeddingFunction weddingFunction;
    @Column(nullable = false, length = 150)
    private String title;
    @Column(nullable = false, length = 500)
    private String message;
    private LocalDate notificationDate;
    private LocalTime notificationTime;
    @Enumerated(EnumType.STRING)
    private AppStatus.notificationStatus status = AppStatus.notificationStatus.PENDING;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private Boolean deleted = false;

}