package com.example.event_management.entity;


import com.example.event_management.common.AppStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@Table(
        name = "Wedding_Guest_Upload",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"wedding_id", "mobile_number"})
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "is_deleted = false")
public class WeddingGuestUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wedding_id", nullable = false)
    private WeddingMaster weddingMaster;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "side_id", nullable = false)
    private WeddingSideMaster side;

    @Column(nullable = false)
    private String guestName;

    @Column(nullable = false)
    private String mobileNumber;

    @Enumerated(EnumType.STRING)
    private AppStatus.RegistrationStatus status;

    @Builder.Default
    @Column(nullable = false)
    private Boolean isDeleted = false;

    private String countryCode;
}

