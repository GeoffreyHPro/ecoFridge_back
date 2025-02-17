package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.error.NotFoundError;
import com.example.demo.model.FoodBatch;
import com.example.demo.payload.FoodBatchRequest;
import com.example.demo.repository.foodBatchRepository.FoodBatchRepositoryImpl;

@Service
public class FoodBatchService {

    @Autowired
    private FoodBatchRepositoryImpl foodBatchRepositoryImpl;

    public void addFoodBatch(String bareCode, FoodBatchRequest foodBatchRequest, String username) throws Exception {
        FoodBatch foodBatch = new FoodBatch(foodBatchRequest.getQuantity(), foodBatchRequest.getExpirationDate());
        foodBatchRepositoryImpl.saveFoodBatch(foodBatch, bareCode, username);
    }

    public List<FoodBatch> getExpiredFoodBatches(String username) {
        return foodBatchRepositoryImpl.getExpiredFoodBatches(username);
    }

    public List<FoodBatch> getSoonExpiredFoodBatches(String username) {
        return foodBatchRepositoryImpl.getSoonExpiredFoodBatches(username);
    }

    public List<FoodBatch> getFoodBatchWithBareCode(String bareCode, String username) {
        return foodBatchRepositoryImpl.getFoodBatchesWithBareCode(bareCode, username);
    }

    public FoodBatch getFoodBatch(int idFoodBatch) throws NotFoundError {
        FoodBatch foodBatch = foodBatchRepositoryImpl.getFoodBatch(idFoodBatch);
        if (foodBatch == null) {
            throw new NotFoundError();
        }
        return foodBatch;
    }

    public void updateFoodBatch(int idFoodBatch, FoodBatchRequest foodBatchRequest) throws NotFoundError {
        foodBatchRepositoryImpl.updateFoodBatch(idFoodBatch, foodBatchRequest);
    }

    public void deleteFoodBatch(int idFoodBatch) throws NotFoundError {
        foodBatchRepositoryImpl.deleteFoodBatch(idFoodBatch);
    }
}