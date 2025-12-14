package com.example.event_management.projection.admin;

import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

public interface WeddingFunctionProjection {

    Long getFunctionId();
    String getFunctionName();
    LocalDate getFunctionDate();
    LocalTime getFunctionStartTime();
    LocalTime getFunctionEndTime();
    String getVenueName();
    String getCity();

    Long getWeddingId();
    Long getSideId();

    interface WeddingMaster {
        Long getWeddingId();
    }

    interface WeddingSideMaster {
        Long getSideId();
        String getSideName();
    }
}
