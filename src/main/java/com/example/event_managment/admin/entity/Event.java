package com.example.event_managment.admin.entity;

import com.example.event_managment.common.AppStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;

    private String venue;
    private String address;
    private String title;

    @Column(name = "register_link")
    private String registerLink;

    @Column(name = "information_link")
    private String informationLink;

    private String category;
    private String description;

    @Column(name = "registration_fees")
    private Double registrationFees;

    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private AppStatus.EventStatus status = AppStatus.EventStatus.UPCOMING;
}
