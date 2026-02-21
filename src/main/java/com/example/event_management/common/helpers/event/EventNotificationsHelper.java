package com.example.event_management.common.helpers.event;

import com.example.event_management.common.AppStatus;
import com.example.event_management.entity.OtpTransactions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventNotificationsHelper {

//    public String sendNotification(AppStatus.OtpType type, String typeValue, OtpTransactions otpTransactionsEntity) {
//        if (type == AppStatus.OtpType.PHONE) {
////            smsService.sendSms(typeValue, "Your OTP is " + otpTransactionsEntity.getOtp() +
////                    " (valid for 10 minutes)");
//            log.info("SMS send successfully on phone number "+typeValue);
//            return "Success";
//        } else if (type == AppStatus.OtpType.EMAIL) {
////              emailService.sendEmail(typeValue, "OTP Verification",
////                    "Your OTP is " + otpTransactionsEntity.getOtp() +
////                            " (valid for 10 minutes)");
//            log.info("Email send successfully on email "+typeValue);
//            return "Success";
//        } else {
//            log.error("Wrong OTP Type");
//            return "Failure";
//        }
//    }
public String sendSms(String phoneNumber, String message) {
    log.info("Sending SMS to " + phoneNumber + ": " + message);
    return "Success";
}

    public String sendEmail(String email, String subject, String body) {
        log.info("Sending Email to " + email + ": " + body);
        return "Success";
    }

}
