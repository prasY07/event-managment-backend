package com.example.event_management.common.helpers.event;


import com.example.event_management.common.AppStatus;
import com.example.event_management.common.helpers.dto.request.ResendOtpDto;
import com.example.event_management.common.helpers.dto.request.SendOtpDto;
import com.example.event_management.common.response.ApiResponse;
import com.example.event_management.entity.OtpTransactions;
import com.example.event_management.repository.IOtpTransactionsRepo;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventOtpHelper {

    private final IOtpTransactionsRepo otpTransactionsRepo;
    private final EventNotificationsHelper eventNotificationsHelper;

    public ResponseEntity<ApiResponse<String>> sendOtp(SendOtpDto sendOtpDto) {
        log.info("Inside send OTP service.");
        String newUserOtpId = UUID.randomUUID().toString();
        log.info("New User OTP id {}", newUserOtpId);
        String message = null;
        String retMessage = null;
        String eventNotificationResponse = null;

        OtpTransactions otpTransactionsEntity = OtpTransactions.builder()
                .userOtpId(newUserOtpId)
                .type(sendOtpDto.getOtpType())
                .typeValue(sendOtpDto.getTypeValue())
                .otp(generateOtp())
                .otpStatus(AppStatus.OtpStatus.PENDING)
                .otpExpiry(LocalDateTime.now().plusMinutes(10)) // 10 min expiry
                .otpCount(1)
                .createdBy(sendOtpDto.getRole())
                .createdTime(LocalDateTime.now())
                .build();

        otpTransactionsRepo.save(otpTransactionsEntity);

        message = "Your OTP is " + otpTransactionsEntity.getOtp() + "(valid for 10 minutes).";
        if ((sendOtpDto.getOtpType().equals(AppStatus.OtpType.PHONE))) {
            eventNotificationResponse = eventNotificationsHelper.sendSms(sendOtpDto.getTypeValue(), message);
        } else {
            eventNotificationResponse = eventNotificationsHelper.sendEmail(sendOtpDto.getTypeValue(),"OTP send", message);
        }
        if (eventNotificationResponse.equalsIgnoreCase("Success")) {
            retMessage = String.format("OTP has been sent successfully to your %s: %s.",
                    sendOtpDto.getOtpType() == AppStatus.OtpType.PHONE ? "phone number" : "email",
                    sendOtpDto.getTypeValue());
        } else {
            retMessage = String.format("OTP has not been sent to your %s: %s.",
                    sendOtpDto.getOtpType() == AppStatus.OtpType.PHONE ? "phone number" : "email",
                    sendOtpDto.getTypeValue());
        }

        return ApiResponse.success(retMessage, otpTransactionsEntity.getUserOtpId()); // return new transaction id
    }

    private String generateOtp() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    public ResponseEntity<ApiResponse<String>> resendOtp(ResendOtpDto resendOtpDto) {

        String message = null;
        String eventNotificationResponse = null;
        OtpTransactions otpTransactionsEntity = otpTransactionsRepo.findByUserOtpId(resendOtpDto.getUserOtpId())
                .orElseThrow(() -> new RuntimeException("Invalid User OTP id"));

        if (otpTransactionsEntity.getOtpExpiry().isBefore(LocalDateTime.now())) {
            otpTransactionsEntity.setOtpStatus(AppStatus.OtpStatus.EXPIRED);
            otpTransactionsRepo.save(otpTransactionsEntity);
            throw new RuntimeException("OTP expired. Please start new transaction.");
        }

        // Check 2 min for resend otp
        if (otpTransactionsEntity.getCreatedTime().isAfter(LocalDateTime.now().minusMinutes(2))) {
            throw new RuntimeException("Resend OTP not allowed before 2 minutes.");
        }

        if (otpTransactionsEntity.getOtpCount() >= 3) {
            throw new RuntimeException("Maximum OTP resend attempts reached.");
        }

        // Reuse same OTP, just extend expiry
        otpTransactionsEntity.setOtpExpiry(LocalDateTime.now().plusMinutes(10)); // refresh 10 min validity
        otpTransactionsEntity.setOtpCount(otpTransactionsEntity.getOtpCount() + 1);

        otpTransactionsRepo.save(otpTransactionsEntity);

        message = "Your OTP is " + otpTransactionsEntity.getOtp() + "(valid for 10 minutes).";
        if ((otpTransactionsEntity.getType().equals(AppStatus.OtpType.PHONE))) {
            eventNotificationResponse = eventNotificationsHelper.sendSms(otpTransactionsEntity.getTypeValue(), message);
        } else {
            eventNotificationResponse = eventNotificationsHelper.sendEmail(otpTransactionsEntity.getTypeValue(),"OTP send", message);
        }
        if (eventNotificationResponse.equalsIgnoreCase("Success")) {
            message = String.format("OTP has been sent successfully to your %s: %s.",
                    otpTransactionsEntity.getType() == AppStatus.OtpType.PHONE ? "phone number" : "email",
                    otpTransactionsEntity.getTypeValue());
        } else {
            message = String.format("OTP has not been sent to your %s: %s.",
                    otpTransactionsEntity.getType() == AppStatus.OtpType.PHONE ? "phone number" : "email",
                    otpTransactionsEntity.getTypeValue());
        }

        return ApiResponse.success(message, otpTransactionsEntity.getUserOtpId()); // return new transaction id
    }

    public ResponseEntity<ApiResponse<String>> verifyOtp(String userOtpId, String otp) {
        OtpTransactions otpTransactionsEntity = otpTransactionsRepo.findByUserOtpId(userOtpId)
                .orElseThrow(() -> new RuntimeException("Invalid transaction id"));

        if (otpTransactionsEntity.getOtpExpiry().isBefore(LocalDateTime.now())) {
            otpTransactionsEntity.setOtpStatus(AppStatus.OtpStatus.EXPIRED);
            otpTransactionsRepo.save(otpTransactionsEntity);
            eventNotificationsHelper.sendSms(otpTransactionsEntity.getTypeValue(), "OTP expired. Please request a new one.");
            return ApiResponse.error("OTP expired. Please request a new one.", null, HttpStatus.BAD_REQUEST);
        }

        if (otpTransactionsEntity.getOtpCount() > 3) {
            eventNotificationsHelper.sendSms(otpTransactionsEntity.getTypeValue(), "Maximum OTP attempts exceeded.");
            return ApiResponse.error("Maximum OTP attempts exceeded.", null, HttpStatus.BAD_REQUEST);
        }

        otpTransactionsEntity.setOtpCount(otpTransactionsEntity.getOtpCount() + 1);

        if (otpTransactionsEntity.getOtp().equals(otp)) {
            otpTransactionsEntity.setOtpStatus(AppStatus.OtpStatus.USED);
            otpTransactionsRepo.save(otpTransactionsEntity);
            return ApiResponse.success("OTP verified successfully", otpTransactionsEntity.getUserOtpId());
        } else {
            otpTransactionsRepo.save(otpTransactionsEntity);
            return ApiResponse.error("Invalid OTP", null, HttpStatus.BAD_REQUEST);
        }
    }

}
