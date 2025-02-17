package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "foods")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idFood")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idFood;

    private String bareCode;

    private String name;

    private String description;

    private String image;

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<FoodBatch> foodBatches = new ArrayList<>();

    public Food() {

    }

    public Food(String bareCode) {
        this.bareCode = bareCode;
        this.image = "default.png";
    }

    public String getBareCode() {
        return bareCode;
    }

    public void setBareCode(String bareCode) {
        this.bareCode = bareCode;
    }

    public int getIdFood() {
        return idFood;
    }

    public List<FoodBatch> getFoodBatches() {
        return foodBatches;
    }

    public void setFoodBatches(List<FoodBatch> foodBatches) {
        this.foodBatches = foodBatches;
    }

    public void addFoodBatches(FoodBatch foodBatch) {
        this.foodBatches.add(foodBatch);
        foodBatch.setFood(this);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

}
