package com.example.demo.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.error.NotFoundError;

@Service
public class ImageService {

    @Value("${image.storage.path}")
    private String imageStoragePath;

    @Autowired
    private FoodService foodService;

    public void saveFile(MultipartFile file, String bareCode) throws IOException {
        foodService.updateFoodImage(bareCode, file);
    }

    public byte[] getImage(String barcode) throws NotFoundError {
        return foodService.getFood(barcode).getImageData();
    }
}
