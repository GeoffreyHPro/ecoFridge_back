package com.example.demo.repository.foodBatchRepository;

import java.util.List;

import com.example.demo.error.NotFoundError;
import com.example.demo.model.FoodBatch;
import com.example.demo.payload.FoodBatchRequest;

public interface CustomFoodBatchRepository {
    void saveFoodBatch(FoodBatch foodBatch, String bareCode, String username) throws Exception;

    List<FoodBatch> getFoodBatchesWithBareCode(String bareCode, String username);

    List<FoodBatch> getExpiredFoodBatches(String username);

    List<FoodBatch> getSoonExpiredFoodBatches(String username);

    FoodBatch getFoodBatch(int idFoodBatch);

    void updateFoodBatch(int idFoodBatch, FoodBatchRequest foodBatchRequest) throws NotFoundError;

    void deleteFoodBatch(int idFoodBatch) throws NotFoundError;
}
