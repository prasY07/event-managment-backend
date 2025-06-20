package com.example.event_management.common.helpers;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileStorageHelper {
    public static String saveImageForEvent(MultipartFile file, Long eventId, String type) {
        try {
            String folderPath = "uploads/event_" + eventId + "/" + type + "/";
            Path dirPath = Paths.get(folderPath);

            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            long unixTimestamp = System.currentTimeMillis() / 1000 + eventId;

            String fileName = unixTimestamp + "_" + file.getOriginalFilename();
            Path filePath = dirPath.resolve(fileName);
            Files.write(filePath, file.getBytes());

            return filePath.toString(); // Or relative path if preferred
        } catch (IOException e) {
            throw new RuntimeException("Failed to store image", e);
        }
    }
}
