package com.example.event_management.entity;

import com.example.event_management.common.AppStatus;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "states" , uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private  String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppStatus.CommonStatus status = AppStatus.CommonStatus.INACTIVE;

}
