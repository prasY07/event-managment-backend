package com.example.event_management.entity;

import jakarta.persistence.*;
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
    @JoinColumn(name = "id", nullable = false)
    private EventMemberType memberType;

    @ManyToOne
    @JoinColumn(name = "day_service_id", nullable = false)
    private DayService dayService;

//    private Boolean allowed; // true = allowed, false = not allowed
}
