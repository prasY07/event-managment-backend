package com.example.event_management.projection.admin;

import com.example.event_management.common.AppStatus;

import java.time.LocalDate;

public interface WeddingListShortProjection {
    Long getId();
    String getGroomName();
    String getBrideName();
    LocalDate getWeddingDate();
    String getCoupleName();
    AppStatus.WeddingStatus getWeddingStatus();
    String getWeddingId();
}
