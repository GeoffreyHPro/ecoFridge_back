package com.example.demo.repository.foodRepository;

import java.util.List;

import com.example.demo.error.NotFoundError;
import com.example.demo.model.Food;
import com.example.demo.payload.FoodUpdateRequest;

public interface CustomFoodRepository {
    boolean saveFood(Food food);

    List<Food> getAllFoods(String username);

    Food getFood(String bareCode);

    void updateFoodImage(String bareCode, String image);

    void deleteFood(String bareCode);

    void updateFood(String barCode, FoodUpdateRequest foodUpdateRequest) throws NotFoundError;
}