package com.example.demo.repository.foodRepository;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.error.NotFoundError;
import com.example.demo.model.Food;
import com.example.demo.payload.FoodUpdateRequest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class FoodRepositoryImpl implements CustomFoodRepository {
    @PersistenceContext
    private EntityManager em;

    public FoodRepositoryImpl(EntityManager emParam) {
        this.em = emParam;
    }

    @Override
    public boolean saveFood(Food food) {
        Food databaseFood = getFood(food.getBareCode());
        if (databaseFood == null) {
            this.em.persist(food);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Food> getAllFoods(String username) {
        String request = "SELECT f FROM Food f " +
                "JOIN f.foodBatches fb " +
                "WHERE fb.username = :username";

        TypedQuery<Food> query = em.createQuery(request, Food.class);
        query.setParameter("username", username);
        List<Food> food = query.getResultList();
        return food;
    }

    @Override
    public Food getFood(String bareCode) {
        try {
            String request = "SELECT f FROM Food f WHERE f.bareCode = :bareCode";
            TypedQuery<Food> query = em.createQuery(request, Food.class);
            query.setParameter("bareCode", bareCode);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void updateFoodImage(String bareCode, String image) {
        Food food = getFood(bareCode);
        food.setImage(image);
        this.em.merge(food);
    }

    @Override
    public void deleteFood(String bareCode) {
        Food food = getFood(bareCode);
        this.em.remove(food.getFoodBatches());
        this.em.remove(food);
    }

    @Override
    public void updateFood(String barCode, FoodUpdateRequest foodUpdateRequest) throws NotFoundError {
        Food food = getFood(barCode);
        if (food != null) {
            food.setDescription(foodUpdateRequest.getDescription());
            food.setName(foodUpdateRequest.getName());
            this.em.merge(food);
        } else {
            throw new NotFoundError();
        }
    }
}