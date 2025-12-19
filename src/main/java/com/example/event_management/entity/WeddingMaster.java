package com.example.event_management.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.event_management.common.AppStatus;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wedding_master", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"wedding_id"})
})
public class WeddingMaster {

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "groom_name", nullable = false)
    private String groomName;

    @Column(name = "bride_name", nullable = false)
    private String brideName;

    @Column(name = "groom_father_name")
    private String groomFatherName;

    @Column(name = "groom_mother_name")
    private String groomMotherName;

    @Column(name = "bride_father_name")
    private String brideFatherName;

    @Column(name = "bride_mother_name")
    private String brideMotherName;

    @Column(name = "wedding_card")
    private String weddingCard;

    @Column(name = "wedding_id", nullable = false, unique = true)
    private String weddingId;

    @Column(name = "couple_name", nullable = false)
    private String coupleDisplayName;

    @Column(name = "wedding_date", nullable = false)
    private LocalDate weddingDate;

    @Column(name = "registration_start_date")
    private LocalDate registrationStartDate;

    @Column(name = "registration_end_date")
    private LocalDate registrationEndDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "wedding_status")
    private AppStatus.WeddingStatus weddingStatus = AppStatus.WeddingStatus.UPCOMING;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AppStatus.WStatus status = AppStatus.WStatus.INACTIVE;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    
}
