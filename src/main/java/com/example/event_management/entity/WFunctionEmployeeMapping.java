package com.example.event_management.entity;



import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "w_function_employee_mapping")

public class WFunctionEmployeeMapping {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "function_id", nullable = false)
    private WeddingFunction function;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    private String roleInFunction; // Coordinator, Support, etc.
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}
