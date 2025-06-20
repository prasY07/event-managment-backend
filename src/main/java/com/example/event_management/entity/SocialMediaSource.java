package com.example.event_management.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "social_media_sources" , uniqueConstraints = {@UniqueConstraint(columnNames = "name")})

public class SocialMediaSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private  String name;
}
