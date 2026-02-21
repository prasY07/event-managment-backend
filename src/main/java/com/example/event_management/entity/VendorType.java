package com.example.event_management.entity;

import com.example.event_management.common.AppStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "vendor_types",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = { "name" })
        }
)
public class VendorType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    private AppStatus.CommonStatus status;



        

}
