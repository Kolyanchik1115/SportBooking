package com.application.sportbooking.service.storage;

import com.application.sportbooking.exception.FileStorageException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private static final String uploadDir = "src/main/resources/files/avatar/";

    @Override
    public String storeFile(MultipartFile file) {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + fileName);
        try {
            Files.write(filePath, file.getBytes());
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName);
        }
        return fileName;
    }
}

