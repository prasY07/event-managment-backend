package com.example.event_management.entity;

import com.example.event_management.common.AppStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "countries", uniqueConstraints = { @UniqueConstraint(columnNames = "name") })

public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, name = "country_code")
    private String countryCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppStatus.CommonStatus status = AppStatus.CommonStatus.INACTIVE;
}
