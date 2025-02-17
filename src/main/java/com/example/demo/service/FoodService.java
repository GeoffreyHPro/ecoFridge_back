package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.NotFound;

import com.example.demo.error.AlreadySavedError;
import com.example.demo.error.NotFoundError;
import com.example.demo.model.Food;
import com.example.demo.payload.FoodUpdateRequest;
import com.example.demo.repository.foodRepository.FoodRepositoryImpl;

@Service
public class FoodService {
    @Autowired
    private FoodRepositoryImpl foodRepositoryImpl;

    public List<Food> getFoods(String username) {
        return foodRepositoryImpl.getAllFoods(username);
    }

    public void save(Food food) throws AlreadySavedError {
        Boolean saved = foodRepositoryImpl.saveFood(food);
        if (!saved) {
            throw new AlreadySavedError();
        }

    }

    public Food getFood(String bareCode) throws NotFoundError {
        Food food = foodRepositoryImpl.getFood(bareCode);
        if (food == null) {
            throw new NotFoundError();
        }
        return food;
    }

    public void updateFoodImage(String bareCode, String image) {
        foodRepositoryImpl.updateFoodImage(bareCode, image);
    }

    public void deleteFood(String bareCode) {
        foodRepositoryImpl.deleteFood(bareCode);
    }

    public void updateFoodInformations(String bareCode, FoodUpdateRequest foodUpdateRequest) throws NotFoundError {
        foodRepositoryImpl.updateFood(bareCode, foodUpdateRequest);
    }
}
