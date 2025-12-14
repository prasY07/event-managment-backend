package com.example.event_management.projection.admin;


import com.example.event_management.common.AppStatus;

public interface EventShortProjection {
    AppStatus.EventStatus getStaus();
    Long getId();
}
