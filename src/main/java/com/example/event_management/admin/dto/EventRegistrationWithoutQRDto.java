package com.example.event_management.admin.dto;

import com.example.event_management.common.AppStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventRegistrationWithoutQRDto {
//     private Long id;

    private String name;

    private String email;

    private Long memberTypeId;

    private String gender;

    private Long heardSourceId;

    private String phoneNumber;
    private Long countryId;
    private String eventId;

    private String address;


    private String zipcode;

    private AppStatus.EventRegistrationAddedBy eventRegistrationAddedBy;


}
