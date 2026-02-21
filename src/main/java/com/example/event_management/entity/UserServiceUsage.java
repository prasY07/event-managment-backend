package com.example.event_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "user_service_usage",
        uniqueConstraints = @UniqueConstraint(columnNames = {"reg_id", "day_service_id", "day_id"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserServiceUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usageId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private EventRegistration registration;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event eventId;

    @Column(name = "service_access_id", nullable = false)
    private Long serviceAccessId;
    
    private java.time.LocalDateTime usedAt = java.time.LocalDateTime.now();
}
