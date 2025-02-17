package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.service.FoodService;
import com.example.demo.service.ImageService;
import com.example.demo.service.JWTUtils;
import com.example.demo.service.UserService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ImageController.class)
public class ImageControllerTest {

    @MockBean
    private FoodService foodService;

    @MockBean
    private UserService userService;

    @MockBean
    private ImageService imageService;

    @MockBean
    private JWTUtils jwtUtils;

    @InjectMocks
    ImageController imageController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin@admin.com")
    public void getDefaultImage() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/image/default.png"))
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @WithMockUser(username = "admin@admin.com")
    public void noGetDefaultImage() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/image/defau.png"))
                .andReturn();
        assertEquals(400, result.getResponse().getStatus());
    }
}
