package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imageData;

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<FoodBatch> foodBatches = new ArrayList<>();

    public Food() {

    }

    public Food(String bareCode) {
        this.bareCode = bareCode;
    }

    public Food(String bareCode, String name, String description) {
        this.bareCode = bareCode;
        this.name = name;
        this.description = description;
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

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

}
