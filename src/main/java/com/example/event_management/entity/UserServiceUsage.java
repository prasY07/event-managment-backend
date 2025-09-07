package com.example.event_management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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

    @ManyToOne
    @JoinColumn(name = "service_acess_id", nullable = false)
    private MemberTypeServiceAccess serviceAccessId;
    
    private java.time.LocalDateTime usedAt = java.time.LocalDateTime.now();
}
