package com.example.event_management.common.helpers.event;


import com.example.event_management.common.AppStatus;
import com.example.event_management.common.helpers.dto.request.ResendOtpDto;
import com.example.event_management.common.helpers.dto.request.SendOtpDto;
import com.example.event_management.common.helpers.dto.request.VerifyOtpDto;
import com.example.event_management.common.response.SendOtpResponse;
import com.example.event_management.entity.OtpTransactions;
import com.example.event_management.repository.IOtpTransactionsRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventOtpHelper {

    private final IOtpTransactionsRepo otpTransactionsRepo;
    private final EventNotificationsHelper eventNotificationsHelper;

    public SendOtpResponse sendOtp(SendOtpDto sendOtpDto) {
        log.info("Inside send OTP service.");
        String role = sendOtpDto.getRole();
        if (role == null || role.isEmpty()) {
            role = "USER";
        }

        String newUserOtpId = UUID.randomUUID().toString();
        log.info("New User OTP id {}", newUserOtpId);
        String message = null;
        String retMessage = null;
        String eventNotificationResponse = null;

        String phone = normalizePhoneNumber(sendOtpDto.getTypeValue());

        String otp = generateOtp();

       

        message = "Your OTP is " + otp + "(valid for 10 minutes).";
        if ((sendOtpDto.getOtpType().equals(AppStatus.OtpType.PHONE))) {
            eventNotificationResponse = eventNotificationsHelper.sendSms(phone, message);
        }
        if ((sendOtpDto.getOtpType().equals(AppStatus.OtpType.EMAIL))) {
            eventNotificationResponse = eventNotificationsHelper.sendEmail(phone, "OTP send", message);
        }
        if (eventNotificationResponse.equalsIgnoreCase("Success")) {
            retMessage = String.format("OTP has been sent successfully to your %s: %s.",
                    sendOtpDto.getOtpType() == AppStatus.OtpType.PHONE ? "phone number" : "email",
                    phone);
        } else {
            retMessage = String.format("OTP has not been sent to your %s: %s.",
                    sendOtpDto.getOtpType() == AppStatus.OtpType.PHONE ? "phone number" : "email",
                    phone);
        }

         OtpTransactions otpTransactionsEntity = OtpTransactions.builder()
                .userOtpId(newUserOtpId)
                .type(sendOtpDto.getOtpType())
                .typeValue(phone)
                .otp(otp)
                .otpStatus(AppStatus.OtpStatus.PENDING)
                .otpExpiry(LocalDateTime.now().plusMinutes(10)) // 10 min expiry
                .otpCount(1)
                .createdBy(role)
                .createdTime(LocalDateTime.now())
                .build();

        otpTransactionsRepo.save(otpTransactionsEntity);

        return new SendOtpResponse(retMessage, newUserOtpId);
    }

    private String generateOtp() {
//        return String.format("%06d", new Random().nextInt(999999));
        return "123456";
    }

    public SendOtpResponse resendOtp(ResendOtpDto resendOtpDto) {
        log.info("Inside resend OTP service.");

        String message = null;
        String eventNotificationResponse = null;
        OtpTransactions otpTransactionsEntity = otpTransactionsRepo.findByUserOtpId(resendOtpDto.getUserOtpId())
                .orElseThrow(() -> new RuntimeException("Invalid User OTP id"));

//        if (otpTransactionsEntity.getOtpExpiry().isBefore(LocalDateTime.now())) {
//            otpTransactionsEntity.setOtpStatus(AppStatus.OtpStatus.EXPIRED);
//            otpTransactionsRepo.save(otpTransactionsEntity);
//            throw new RuntimeException("OTP expired. Please start new transaction.");
//        }

        // Check 2 min for resend otp
        if (otpTransactionsEntity.getCreatedTime().isAfter(LocalDateTime.now().minusMinutes(2))) {
            throw new RuntimeException("Resend OTP not allowed before 2 minutes.");
        }

        if (otpTransactionsEntity.getOtpCount() >= 3) {
            throw new RuntimeException("Maximum OTP resend attempts reached.");
        }

        String newOtp = generateOtp();
        otpTransactionsEntity.setOtp(newOtp);
        otpTransactionsEntity.setOtpExpiry(LocalDateTime.now().plusMinutes(10)); // refresh 10 min validity
        otpTransactionsEntity.setOtpCount(otpTransactionsEntity.getOtpCount() + 1);

        otpTransactionsRepo.save(otpTransactionsEntity);

        message = "Your OTP is " + otpTransactionsEntity.getOtp() + "(valid for 10 minutes).";
        if ((otpTransactionsEntity.getType().equals(AppStatus.OtpType.PHONE))) {
            eventNotificationResponse = eventNotificationsHelper.sendSms(otpTransactionsEntity.getTypeValue(), message);
        }
        if ((otpTransactionsEntity.getType().equals(AppStatus.OtpType.EMAIL))) {
            eventNotificationResponse = eventNotificationsHelper.sendEmail(otpTransactionsEntity.getTypeValue(), "OTP send", message);
        }
        if (eventNotificationResponse.equalsIgnoreCase("Success")) {
            message = String.format("OTP has been resent successfully to your %s: %s.",
                    otpTransactionsEntity.getType() == AppStatus.OtpType.PHONE ? "phone number" : "email",
                    otpTransactionsEntity.getTypeValue());
        } else {
            message = String.format("OTP has not been resent to your %s: %s.",
                    otpTransactionsEntity.getType() == AppStatus.OtpType.PHONE ? "phone number" : "email",
                    otpTransactionsEntity.getTypeValue());
        }

        return new SendOtpResponse(message, otpTransactionsEntity.getUserOtpId());
    }

    public String verifyOtp(VerifyOtpDto verifyOtpDto) {
        log.info("Inside verify OTP service");
        OtpTransactions otpTransactionsEntity = otpTransactionsRepo.findByUserOtpId(verifyOtpDto.getUserOtpId())
                .orElseThrow(() -> new RuntimeException("Invalid transaction id"));

        LocalDateTime now = LocalDateTime.now();

        // Check if OTP expired
        if (otpTransactionsEntity.getOtpExpiry().isBefore(now)) {
            otpTransactionsEntity.setOtpStatus(AppStatus.OtpStatus.EXPIRED);
            otpTransactionsRepo.save(otpTransactionsEntity);

            if (otpTransactionsEntity.getType() == AppStatus.OtpType.PHONE) {
                String phone = normalizePhoneNumber(otpTransactionsEntity.getTypeValue());
                eventNotificationsHelper.sendSms(phone, "OTP expired. Please request a new one.");
            }
            if ((otpTransactionsEntity.getType().equals(AppStatus.OtpType.EMAIL))) {
                eventNotificationsHelper.sendEmail(otpTransactionsEntity.getTypeValue(), "OTP Expired", "OTP expired. Please request a new one.");
            }

            throw new RuntimeException("OTP expired. Please request a new one.");
        }

        if (otpTransactionsEntity.getOtpCount() >= 3) {
            throw new RuntimeException("Maximum OTP attempts exceeded.");
        }

        otpTransactionsEntity.setOtpCount(otpTransactionsEntity.getOtpCount() + 1);

        if (otpTransactionsEntity.getOtp().equals(verifyOtpDto.getOtp())) {
            otpTransactionsEntity.setOtpStatus(AppStatus.OtpStatus.USED);
            otpTransactionsRepo.save(otpTransactionsEntity);
            return otpTransactionsEntity.getUserOtpId();
        } else {
            otpTransactionsRepo.save(otpTransactionsEntity);
            throw new RuntimeException("Invalid OTP");
        }
    }


    public static String normalizePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }

        // Remove spaces, dashes, brackets
        String cleaned = phoneNumber.replaceAll("[\\s\\-()]", "").trim();

        // Add + if missing
        if (!cleaned.startsWith("+")) {
            cleaned = "+" + cleaned;
        }

        // Validate E.164 format (max 15 digits after country code)
        if (!cleaned.matches("^\\+[1-9]\\d{6,14}$")) {
            throw new IllegalArgumentException(
                    "Invalid phone number format. Include country code, e.g., +919876543210");
        }

        return cleaned;
    }
}

