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
        name = "wedding_side_master",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = { "side_name" })
        }
)
public class WeddingSideMaster {
    
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "side_id")
    private Integer sideId;

    @Column(name = "side_name", nullable = false, unique = true, length = 50)
    private String sideName;

    @Column(name = "description", length = 255)
    private String description;

        @Enumerated(EnumType.STRING)
    private AppStatus.CommonStatus status;
}
