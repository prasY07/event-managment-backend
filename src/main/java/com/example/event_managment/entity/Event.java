package com.example.event_managment.entity;

import java.time.LocalDate;

import com.example.event_managment.common.AppStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "events", uniqueConstraints = { @UniqueConstraint(columnNames = "event_id") })
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;

    @Column(name = "registration_end_date")
    private LocalDate registrationEndDate;

    private String venue;
    private String address;
    private String title;

    @Column(name = "event_id", unique = true)
    private String eventId;

    private String category;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    private String image;

    @Column(name = "privacy_policy", columnDefinition = "TEXT")
    private String privacyPolicy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private AppStatus.EventStatus eventStatus = AppStatus.EventStatus.UPCOMING;

    @Enumerated(EnumType.STRING)
    private AppStatus.EStatus status = AppStatus.EStatus.INACTIVE;
}
