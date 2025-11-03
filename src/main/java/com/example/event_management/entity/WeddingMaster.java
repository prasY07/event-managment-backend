package com.example.event_management.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wedding_master", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"wedding_id"})
})
public class WeddingMaster {

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String groomName;
    private String brideName;
    private String groomFatherName;
    private String groomMotherName;
    private String brideFatherName;
    private String brideMotherName;

    @Column(name = "wedding_card" , nullable = true)
    private String weddingCard;

     @Column(name = "wedding_id" , nullable = false , unique = true)
    private String weddingId;

    private String coupleName;

    private LocalDate weddingDate;
    private LocalDate registrationStartDate;
    private LocalDate registrationEndDate;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

        // private List<WeddingFunction> functions;

    
}
