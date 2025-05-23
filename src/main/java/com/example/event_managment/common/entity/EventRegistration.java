package com.example.event_managment.common.entity;


import com.example.event_managment.common.AppStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "event_registration")
@Getter
@Setter
public class EventRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @ManyToOne
    @JoinColumn(name = "member_type_id")
    private EventMemberType memberTypeId;

    private String gender;

    @ManyToOne
    @JoinColumn(name = "heard_source_id")
    private SocialMediaSource heardSourceId;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    private String address;

    @Column(name = "qr_code", length = 500)
    private String qrCode;

    private String zipcode;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private AppStatus.EventRegistrationAddedBy addedBy = AppStatus.EventRegistrationAddedBy.SELF;

    @PrePersist
    protected void onCreate() {
        createdAt = updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
