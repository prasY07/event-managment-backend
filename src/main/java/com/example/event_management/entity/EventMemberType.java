package com.example.event_management.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "event_member_types")

public class EventMemberType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event eventId;

    @Column(name = "member_type_name",nullable = false)
    private String memberTypeName;

    @Column(name = "entry_fees", nullable = false)
    private BigDecimal entryFees = BigDecimal.valueOf(0.00);


}
