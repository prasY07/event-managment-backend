package com.example.event_management.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "business_details")
public class BusinessDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fname", nullable = false, length = 100)
    private String fname;

    @Column(name = "lname", nullable = false, length = 100)
    private String lname;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

    @Column(name = "email", nullable = false, length = 150)
    private String email;

    @Column(name = "company_name", nullable = false, length = 150)
    private String companyName;

    @Column(name = "company_address", nullable = false, length = 255)
    private String companyAddress;

    @Column(name = "business_summary", columnDefinition = "TEXT")
    private String businessSummary;

    @Column(name = "eema_member", nullable = false)
    @Enumerated(EnumType.STRING)
    private EemaMember eemaMember;

    @Column(name = "emaa", nullable = false)
    @Enumerated(EnumType.STRING)
    private Emaa emaa;

    @Column(name = "categories", nullable = false, length = 255)
    private String categories;

    // Enums
    public enum Gender {
        Male, Female, Other
    }

    public enum EemaMember {
        Yes, No
    }

    public enum Emaa {
        Yes, No
    }
}