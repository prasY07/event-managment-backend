package com.example.event_managment.admin.entity;

import com.example.event_managment.common.AppStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "events",uniqueConstraints = {@UniqueConstraint(columnNames = "event_id")})
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private LocalDate startDate;
    private LocalDate endDate;

    private String venue;
    private String address;
    private String title;

    @Column(name = "event_id",unique = true)
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
