package com.goodamcodes.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class ImageService {
    @Value("${image.path}")
    private String imageStoragePath;

    public String saveImage(MultipartFile imageFile) {
        try {
            String fileName = imageFile.getOriginalFilename();
            Path imagePath = Paths.get(imageStoragePath, fileName);
            Files.copy(imageFile.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store image", e);
        }
    }

    public void deleteImage(String filename) {
        try {
            Path imagePath = Paths.get(imageStoragePath, filename);
            if (Files.exists(imagePath)) {
                Files.delete(imagePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete image", e);
        }
    }

    public String handleFilesUpload(List<MultipartFile> files, int index) {
        if (files != null && files.size() > index) {
            MultipartFile file = files.get(index);
            if (file != null && !file.isEmpty()) {
                return saveImage(file);
            }
        }
        return null;
    }

}
