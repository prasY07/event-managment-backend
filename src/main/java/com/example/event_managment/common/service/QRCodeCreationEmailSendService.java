package com.example.event_managment.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class QRCodeCreationEmailSendService {

    @Autowired
    private QRCodeService qrCodeService;

    @Autowired
    private EmailService emailService;

    @Async
    public void processRegistration(Long eventId , String registrationId, String email) {
        try {

            String folderPath = "uploads/qr_codes" + eventId ;
            Path dirPath = Paths.get(folderPath);

            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }
            String path = folderPath + registrationId + ".png";


            qrCodeService.generateQRCodeImage(registrationId, path);
            emailService.sendEmailWithAttachment(
                    email,
                    "Your QR Code",
                    "Here is your QR Code containing your registration ID: " + registrationId,
                    path
            );
        } catch (Exception ex) {
            // Just log the error if you don't want to store or retry
            ex.printStackTrace();
        }
    }
}
