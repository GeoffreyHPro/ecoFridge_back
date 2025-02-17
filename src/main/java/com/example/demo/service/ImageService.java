package com.example.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    @Value("${image.storage.path}")
    private String imageStoragePath;

    @Autowired
    private FoodService foodService;

    public String generateSingleFilename(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        return UUID.randomUUID() + extension;
    }

    public void saveFile(MultipartFile file, String bareCode) throws IOException {
        String singleFileName = generateSingleFilename(file);
        Path filePath = Paths.get(imageStoragePath, "/" + singleFileName);
        Files.createDirectories(filePath.getParent());
        file.transferTo(filePath);

        foodService.updateFoodImage(bareCode, singleFileName);
    }

    public Resource getImage(String filename) {
        return new org.springframework.core.io.FileSystemResource(
                Paths.get(imageStoragePath).resolve(filename).toFile());
    }
}
