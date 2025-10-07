package com.example.event_management.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardCountResponse {

    private Long totalEvents;
    private Long totalUsers;

}
