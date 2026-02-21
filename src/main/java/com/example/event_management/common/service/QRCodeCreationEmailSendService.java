package com.example.event_management.common.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.event_management.entity.EventRegistration;
import com.example.event_management.repository.IEventRegistrationRepo;

@Service
public class QRCodeCreationEmailSendService {

    @Autowired
    private QRCodeService qrCodeService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private IEventRegistrationRepo iEventRegistrationRepo;

    @Async
    public void processRegistration(Long eventId, String registrationId, String email) {
        try {

            String folderPath = "uploads/event_" + eventId + "/qr_codes/";
            Path dirPath = Paths.get(folderPath);

            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }
            String path = folderPath + registrationId + ".png";
            String fullPath = folderPath + path;
            Path filePath = Paths.get(fullPath);
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }
            qrCodeService.generateQRCodeImage(registrationId, path);
            // emailService.sendEmailWithAttachment(
            // email,
            // "Your QR Code",
            // "Here is your QR Code containing your registration ID: " + registrationId,
            // path
            // );
            EventRegistration registration = iEventRegistrationRepo.findByRegistrationId(registrationId);
            if (registration != null) {
                registration.setQrCode(fullPath);
                iEventRegistrationRepo.save(registration);
            }
        } catch (Exception ex) {
            // Just log the error if you don't want to store or retry
            ex.printStackTrace();
        }
    }
}
