package com.example.event_management.common;

public class AppStatus {

    public enum UserStatus {
        ACTIVE,
        INACTIVE,
        SUSPENDED
    }

    public enum UserRole {
        MASTER_ADMIN,
        EVENT_MANAGER,
        INDIVIDUAL
    }

    public enum EStatus {
        ACTIVE,
        INACTIVE,
    }

    public enum EventStatus {
        UPCOMING,
        ONGOING,
        COMPLETED,
        CANCELLED
    }

    public enum BookingStatus {
        PENDING,
        CONFIRMED,
        REJECTED,
        CANCELLED
    }

    public enum EventRegistrationAddedBy {
        ADMIN,
        SELF,
    }
}
