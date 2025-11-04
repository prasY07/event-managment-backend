package com.example.event_management.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wedding_function")
public class WeddingFunction {

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long functionId;

    @ManyToOne
    @JoinColumn(name = "wedding_id", nullable = false)
    private WeddingMaster weddingMaster;

    @ManyToOne
    @JoinColumn(name = "side_id", nullable = false)
    private WeddingSideMaster side;

    private String functionName;
    private LocalDate functionDate;
    private LocalTime functionStartTime;
    private LocalTime functionEndTime;

    private String venueName;
    private String venueAddress;
    private String city;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    // @OneToMany(mappedBy = "function", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<FunctionEmployeeMapping> employeeMappings;

    // @OneToMany(mappedBy = "function", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<FunctionVendorMapping> vendorMappings;
    
}
