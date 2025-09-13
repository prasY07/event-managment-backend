package com.example.event_management.entity;

import com.example.event_management.common.AppStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "admins", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "email" })
})
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppStatus.AdminStatus status = AppStatus.AdminStatus.INACTIVE;
}
