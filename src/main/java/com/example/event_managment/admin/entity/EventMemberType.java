package com.example.event_managment.admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@AllArgsConstructor
//@NoArgsConstructor
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


}
