package com.example.event_management.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessDetailsDTO {
    
     private Integer id;
    private String fname;
    private String lname;
    private String gender;
    private String phoneNumber;
    private String email;
    private String companyName;
    private String gst;
    private String companyAddress;
    private String businessSummary;
}
