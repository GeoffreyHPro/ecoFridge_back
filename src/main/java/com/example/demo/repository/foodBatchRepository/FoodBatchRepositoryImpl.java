package com.example.demo.repository.foodBatchRepository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.error.NotFoundError;
import com.example.demo.model.Food;
import com.example.demo.model.FoodBatch;
import com.example.demo.model.User;
import com.example.demo.payload.FoodBatchRequest;
import com.example.demo.repository.foodRepository.FoodRepositoryImpl;
import com.example.demo.repository.userRepository.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class FoodBatchRepositoryImpl implements CustomFoodBatchRepository {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private FoodRepositoryImpl foodRepositoryImpl;

    @Autowired
    private UserRepository userRepository;

    public FoodBatchRepositoryImpl(EntityManager emParam) {
        this.em = emParam;
    }

    @Override
    public void saveFoodBatch(FoodBatch foodBatch, String bareCode, String username) throws Exception {

        Food food = foodRepositoryImpl.getFood(bareCode);
        if (food == null) {
            throw new Exception("Food not found");
        }
        User user = userRepository.findByEmail(username);
        foodBatch.setUsername(user.getUsername());
        foodBatch.setFood(food);
        food.addFoodBatches(foodBatch);
        this.em.persist(foodBatch);
    }

    @Override
    public List<FoodBatch> getFoodBatchesWithBareCode(String bareCode, String username) {
        try {
            String request = "SELECT fb FROM Food f " +
                    "JOIN f.foodBatches fb " +
                    "WHERE f.bareCode = :bareCode AND fb.username = :username";
            TypedQuery<FoodBatch> query = em.createQuery(request, FoodBatch.class);
            query.setParameter("username", username);
            query.setParameter("bareCode", bareCode);
            List<FoodBatch> foodBatches = query.getResultList();
            return foodBatches;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'exécution de la requête", e);
        }
    }

    @Override
    public List<FoodBatch> getExpiredFoodBatches(String username) {
        try {
            String request = "SELECT f FROM FoodBatch f WHERE f.username = :username AND f.expirationDate BETWEEN f.expirationDate AND :actualDate";
            TypedQuery<FoodBatch> query = em.createQuery(request, FoodBatch.class);
            query.setParameter("username", username);
            query.setParameter("actualDate", LocalDateTime.now());
            List<FoodBatch> foodBatches = query.getResultList();
            return foodBatches;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<FoodBatch> getSoonExpiredFoodBatches(String username) {
        try {
            String request = "SELECT f FROM FoodBatch f WHERE f.username = :username AND f.expirationDate BETWEEN :startDate AND :endDate";
            TypedQuery<FoodBatch> query = em.createQuery(request, FoodBatch.class);
            query.setParameter("username", username);
            query.setParameter("startDate", LocalDateTime.now());
            query.setParameter("endDate", LocalDateTime.now().plusDays(5));
            List<FoodBatch> foodBatches = query.getResultList();
            return foodBatches;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public FoodBatch getFoodBatch(int idFoodBatch) {
        try {
            String request = "SELECT fb FROM FoodBatch fb WHERE fb.idFoodBatch = :idFoodBatch";
            TypedQuery<FoodBatch> query = em.createQuery(request, FoodBatch.class);
            query.setParameter("idFoodBatch", idFoodBatch);
            FoodBatch foodBatch = query.getSingleResult();
            return foodBatch;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void updateFoodBatch(int idFoodBatch, FoodBatchRequest foodBatchRequest) throws NotFoundError {
        FoodBatch foodBatch = getFoodBatch(idFoodBatch);
        if (foodBatch != null) {
            foodBatch.setExpirationDate(foodBatchRequest.getExpirationDate());
            foodBatch.setQuantity(foodBatchRequest.getQuantity());
            this.em.merge(foodBatch);
        } else {
            throw new NotFoundError();
        }
    }

    @Override
    public void deleteFoodBatch(int idFoodBatch) throws NotFoundError {
        FoodBatch foodBatch = getFoodBatch(idFoodBatch);
        if (foodBatch != null) {
            this.em.remove(foodBatch);
        } else {
            throw new NotFoundError();
        }
    }
}