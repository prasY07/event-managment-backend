package com.example.event_management.entity;

import com.example.event_management.common.AppStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "otp_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OtpTransactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userOtpId;   // unique transaction id

    @Enumerated(EnumType.STRING)
    private AppStatus.OtpType type;

    private String typeValue;
    private String otp;

    @Enumerated(EnumType.STRING)
    private AppStatus.OtpStatus otpStatus;

    private LocalDateTime otpExpiry;
    private Integer otpCount;

    private String createdBy;

    private LocalDateTime createdTime;

}
