package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.model.Food;
import com.example.demo.reponses.ListResponse;
import com.example.demo.repository.foodRepository.FoodRepositoryImpl;
import com.example.demo.service.FoodMapper;
import com.example.demo.service.FoodService;
import com.example.demo.service.JWTUtils;
import com.example.demo.service.UserService;
import com.example.demo.shared.ResponseAsString;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(FoodController.class)
public class FoodControllerTest {
    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private FoodMapper foodMapper;

    @MockBean
    private FoodService foodService;

    @MockBean
    private UserService userService;

    @MockBean
    private FoodRepositoryImpl foodRepositoryImpl;

    @MockBean
    private JWTUtils jwtUtils;

    @InjectMocks
    FoodController foodController;

    @InjectMocks
    ResponseAsString responseAsString;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin@admin.com")
    public void GetFood() throws Exception {
        Food food = new Food();
        food.setBareCode("0001");
        List<Food> listFood = new ArrayList<>();
        listFood.add(food);
        ListResponse response = new ListResponse(listFood);

        Mockito.when(foodService.getFoods("admin@admin.com")).thenReturn(listFood);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/food"))
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());

        assertEquals(responseAsString.getString(response), result.getResponse().getContentAsString());
    }
}
