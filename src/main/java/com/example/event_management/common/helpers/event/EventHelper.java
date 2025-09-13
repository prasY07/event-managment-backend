package com.example.event_management.common.helpers.event;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.event_management.common.AppStatus;
import com.example.event_management.entity.Event;

public class EventHelper {

    public static List<String> parseUniqueCommaSeparatedValues(String input) {
        if (input == null || input.trim().isEmpty()) {
            return List.of();
        }

        return Arrays.stream(input.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .distinct()
                .collect(Collectors.toList());
    }

    public static String createUserUniqueRegistrationID() {
        return "REG-" + System.currentTimeMillis();
    }

    public static String createUniqueEventID() {
        return String.valueOf(System.currentTimeMillis());
    }

    public static Map<String, Object> eventRegistrationCases(Event event) {
        Map<String, Object> result = new HashMap<>();

        if (event.getStatus() != AppStatus.EStatus.ACTIVE) {
            result.put("status", false);
            result.put("msg", "Event is not active. You cannot register for this event");
            return result;
        }
        if (LocalDate.now().isAfter(event.getRegistrationEndDate())) {
            result.put("status", false);
            result.put("msg", "Registration has been closed for this event");
            return result;
        }
        result.put("status", true);
        return result;

    }


}
