package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dto.FoodBatchDTO;
import com.example.demo.dto.FoodDTO;
import com.example.demo.model.Food;
import com.example.demo.model.FoodBatch;

@Service
public class FoodMapper {
    public FoodDTO toFoodDTO(Food food) {
        FoodDTO foodDTO = new FoodDTO();
        foodDTO.setIdFood(food.getIdFood());
        foodDTO.setBareCode(food.getBareCode());
        foodDTO.setImage(food.getImage());
        foodDTO.setName(food.getName());
        foodDTO.setDescription(food.getDescription());
        return foodDTO;
    }

    public FoodBatchDTO toFoodBatchDTO(FoodBatch foodBatch) {
        FoodBatchDTO foodBatchDTO = new FoodBatchDTO();
        foodBatchDTO.setIdFoodBatch(foodBatch.getIdFoodBatch());
        foodBatchDTO.setQuantity(foodBatch.getQuantity());
        foodBatchDTO.setExpirationDate(foodBatch.getExpirationDate());
        foodBatchDTO.setUsername(foodBatch.getUsername());
        if (foodBatch.getFood() != null) {
            foodBatchDTO.setFood(toFoodDTO(foodBatch.getFood()));
        }
        return foodBatchDTO;
    }
}
