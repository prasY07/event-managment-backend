package com.example.event_management.entity;

import com.example.event_management.common.AppStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "vendors",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = { "email" }),
                @UniqueConstraint(columnNames = { "country_id", "phone_number" })
        }
)
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    // New country relation
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", referencedColumnName = "id", nullable = false)
    private Country country;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "state_id", referencedColumnName = "id", nullable = false)
    private State state;

    @ManyToOne
    @JoinColumn(name = "vendor_type_id",  referencedColumnName = "id", nullable = false)
    private VendorType vendorTypeId;

    @Column(nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    private AppStatus.CommonStatus status;
}
