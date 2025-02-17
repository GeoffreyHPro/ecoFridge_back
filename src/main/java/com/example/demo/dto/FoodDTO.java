package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FoodDTO {
    private int idFood;
    private String bareCode;
    private String image;
    private String name;
    private String description;
}