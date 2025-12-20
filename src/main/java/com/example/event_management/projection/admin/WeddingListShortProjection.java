package com.example.event_management.projection.admin;

import java.time.LocalDate;

public interface WeddingListShortProjection {
    String getWeddingId();
    String getGroomName();
    String getBrideName();
    LocalDate getWeddingDate();
    String getCoupleName();
}
