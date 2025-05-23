package com.example.event_managment.admin.entity;

import com.example.event_managment.common.AppStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users" , uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private  String name;

    @Column(nullable = false, unique = true)
    private  String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppStatus.UserStatus status = AppStatus.UserStatus.ACTIVE;
}
