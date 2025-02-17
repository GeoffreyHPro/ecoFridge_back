package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

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

import com.example.demo.dto.FoodBatchDTO;
import com.example.demo.error.NotFoundError;
import com.example.demo.model.FoodBatch;
import com.example.demo.reponses.payload.MessagePayload;
import com.example.demo.repository.foodRepository.FoodRepositoryImpl;
import com.example.demo.service.FoodBatchService;
import com.example.demo.service.FoodMapper;
import com.example.demo.service.JWTUtils;
import com.example.demo.service.UserService;
import com.example.demo.shared.ResponseAsString;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(FoodBatchesController.class)
public class FoodBatchControllerTest {
    @MockBean
    PasswordEncoder passwordEncoder;

    @MockBean
    FoodBatchService foodBatchService;

    @MockBean
    FoodMapper foodMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private FoodRepositoryImpl foodRepositoryImpl;

    @MockBean
    private JWTUtils jwtUtils;

    @InjectMocks
    FoodBatchesController foodBatchesController;

    @InjectMocks
    ResponseAsString responseAsString;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void GetFoodBatchWithoutAuth() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/food"))
                .andReturn();
        assertEquals(401, result.getResponse().getStatus());
    }

    @Test
    @WithMockUser(username = "admin@admin.com")
    public void GetFoodBatchWithIdThrowsException() throws Exception {
        Mockito.when(foodBatchService.getFoodBatch(0)).thenThrow(new NotFoundError());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/foodbatch/id/0"))
                .andReturn();
        assertEquals(404, result.getResponse().getStatus());
        assertEquals(responseAsString.getString(new MessagePayload("This foodbatch is not found")),
                result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(username = "admin@admin.com")
    public void GetFoodBatchWithIdGiveFoodBatch() throws Exception {
        FoodBatch foodBatch = new FoodBatch(20, LocalDateTime.of(2000, 12, 1, 0, 0));
        FoodBatchDTO foodBatchDTO = new FoodBatchDTO();

        Mockito.when(foodBatchService.getFoodBatch(1)).thenReturn(foodBatch);
        Mockito.when(foodMapper.toFoodBatchDTO(foodBatch)).thenReturn(foodBatchDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/foodbatch/id/1"))
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
        assertEquals(responseAsString.getString(foodBatchDTO), result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(username = "admin@admin.com")
    public void DeleteFoodBatch() throws Exception {
        FoodBatch foodBatch = new FoodBatch(20, LocalDateTime.of(2000, 12, 1, 0, 0));

        Mockito.when(foodBatchService.getFoodBatch(1)).thenReturn(foodBatch);
        Mockito.doNothing().when(foodBatchService).deleteFoodBatch(1);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .delete("/foodbatch/1")
                .with(csrf()))
                .andReturn();

        assertEquals(200, result.getResponse().getStatus());
        assertEquals(responseAsString.getString(new MessagePayload("This foodbatch is correctly deleted")), result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(username = "admin@admin.com")
    public void DeleteFoodBatchThrowsException() throws Exception {
        Mockito.doThrow(new NotFoundError()).when(foodBatchService).deleteFoodBatch(1);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .delete("/foodbatch/1")
                .with(csrf()))
                .andReturn();

        assertEquals(404, result.getResponse().getStatus());
        assertEquals(responseAsString.getString(new MessagePayload("This foodbatch is not found")), result.getResponse().getContentAsString());
    }
}
