package com.example.event_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "day_services")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DayService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dayServiceId;

    @ManyToOne
    @JoinColumn(name = "day_id", nullable = false)
    private EventDay eventDay;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private EventService eventService;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private Event event;
}
