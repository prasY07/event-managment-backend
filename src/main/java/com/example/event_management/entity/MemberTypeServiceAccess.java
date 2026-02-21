package com.example.event_management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "member_type_service_access")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberTypeServiceAccess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accessId;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event eventId;

    @ManyToOne
    @JoinColumn(name = "member_type_id", nullable = false)
    private EventMemberType memberType;

    @ManyToOne
    @JoinColumn(name = "day_id", nullable = false)
    private EventDay dayId;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private EventService serviceId;

   
}
