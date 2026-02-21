package com.example.event_management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "wedding_guest")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeddingGuest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long weddingId;
    private String fullName;
    private String mobileNumber;
    private String email;
    private String gender;

    // ONWARD
    private String onwardMode;
    private LocalDate onwardArrivalDate;
    private LocalTime onwardArrivalTime;
    private String onwardDepartureCity;
    private String onwardDestinationCity;
    private String onwardCarrierName;
    private String onwardCarrierNumber;
    private String onwardTicketPath;

    // RETURN
    private String returnMode;
    private LocalDate returnDepartureDate;
    private LocalTime returnDepartureTime;
    private String returnDepartureCity;
    private String returnDestinationCity;
    private String returnCarrierName;
    private String returnCarrierNumber;
    private String returnTicketPath;

    @OneToMany(mappedBy = "guest", cascade = CascadeType.ALL)
    private List<CoPassenger> coPassengers;

}