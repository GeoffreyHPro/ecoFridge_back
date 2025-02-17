package com.example.demo.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "foodbatches")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idFoodBatch")
public class FoodBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idFoodBatch;

    private int quantity;

    private LocalDateTime expirationDate;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;

    private String username;

    public FoodBatch() {

    }

    public FoodBatch(int quantity, LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
        this.quantity = quantity;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public int getIdFoodBatch() {
        return idFoodBatch;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
