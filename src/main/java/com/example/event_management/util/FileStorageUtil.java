package com.example.event_management.util;

import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class FileStorageUtil {

    public static String saveWeddingRegistrationFile(
            MultipartFile file,
            Long weddingId,
            String journeyType) {

        try {
            if (file == null || file.isEmpty()) {
                throw new IllegalArgumentException("File is empty");
            }

            String baseDir = "uploads/wedding"
                    + File.separator + "wedding-" + weddingId
                    + File.separator + "registrations"
                    + File.separator + journeyType.toLowerCase();

            Path dirPath = Paths.get(baseDir);
            Files.createDirectories(dirPath);

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename().replaceAll("\\s+", "_");
            Path filePath = dirPath.resolve(fileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return baseDir + File.separator + fileName;

        } catch (IOException e) {
            throw new RuntimeException("Failed to store wedding registration file", e);
        }
    }
}
