package com.example.event_managment.common.helpers;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EventHelper {

    // private final IEventRegistrationRepo iEventRegistrationRepo;

    // @Autowired
    // public EventHelper(IEventRegistrationRepo iEventRegistrationRepo) {
    //     this.iEventRegistrationRepo = iEventRegistrationRepo;
    // }

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

    // public String createUserUniqueRegistrationID() {
    //     String id;
    //     do {
    //         id = "REG-" + System.currentTimeMillis();
    //     } while (iEventRegistrationRepo.existsByRegistrationId(id));
    //     return id;
    // }

    public static String createUserUniqueRegistrationID() {
        return   "REG-" + System.currentTimeMillis();
    }

}
