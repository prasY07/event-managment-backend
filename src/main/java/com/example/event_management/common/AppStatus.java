package com.example.event_management.common;

public class AppStatus {

    public enum EmployeeStatus {
        ACTIVE,
        INACTIVE,
    }

    public enum UserStatus {
        ACTIVE,
        INACTIVE,
        SUSPENDED
    }

    public enum AdminStatus {
        ACTIVE,
        INACTIVE,
        SUSPENDED
    }

    public enum UserRole {
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

    public enum EventFoc {
        YES,
        NO
    }

    public enum OtpType {
        PHONE, EMAIL
    }

    public enum OtpStatus {
        PENDING, EXPIRED, USED
    }

    public enum CommonStatus {
        ACTIVE,
        INACTIVE,
    }

    public enum ETYPE {
        COORDINATOR,
        SUPPORT,
    }


    public enum WeddingStatus {
        UPCOMING,
        ONGOING,
        COMPLETED,
        CANCELLED
    }


    public enum WStatus {
        ACTIVE,
        INACTIVE,
    }

    public enum notificationStatus {
        PENDING,
        SENT,
        FAILED
    }


}
