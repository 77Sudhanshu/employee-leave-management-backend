package com.sudhanshu.employeeleavemanagementsystem.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    // ===========================
    // Certificate Files
    // ===========================

    public String storeCertificate(MultipartFile file) {
        return store(file, "certificates");
    }

    public Path loadCertificate(String fileName) {
        return Paths.get(uploadDir, "certificates").resolve(fileName);
    }

    // ===========================
    // Profile Images
    // ===========================

    public String storeProfileImage(MultipartFile file) {
        return store(file, "profiles");
    }

    public Path loadProfileImage(String fileName) {
        return Paths.get(uploadDir, "profiles").resolve(fileName);
    }

    // ===========================
    // Common Storage Method
    // ===========================

    private String store(MultipartFile file, String folderName) {

        try {

            Path uploadPath = Paths.get(uploadDir, folderName);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            Files.copy(
                    file.getInputStream(),
                    uploadPath.resolve(fileName),
                    StandardCopyOption.REPLACE_EXISTING
            );

            return fileName;

        } catch (IOException e) {
            throw new RuntimeException("Could not store file.", e);
        }
    }
}