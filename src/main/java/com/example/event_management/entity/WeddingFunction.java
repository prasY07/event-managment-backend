package com.example.event_management.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wedding_function")
@Where(clause = "is_deleted = false")
@Builder
@EntityListeners(AuditingEntityListener.class)
public class WeddingFunction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long functionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wedding_id", nullable = false)
    private WeddingMaster weddingMaster;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "side_id", nullable = false)
    private WeddingSideMaster side;

    private String functionName;
    private LocalDate functionDate;
    private LocalTime functionStartTime;
    private LocalTime functionEndTime;

    private String venueName;
    private String venueAddress;
    private String city;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;
    private LocalDateTime deletedAt;


    // @OneToMany(mappedBy = "function", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<FunctionEmployeeMapping> employeeMappings;

    // @OneToMany(mappedBy = "function", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<FunctionVendorMapping> vendorMappings;

}
