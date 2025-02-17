package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.FoodDTO;
import com.example.demo.error.AlreadySavedError;
import com.example.demo.error.NotFoundError;
import com.example.demo.model.Food;
import com.example.demo.payload.FoodUpdateRequest;
import com.example.demo.reponses.ListResponse;
import com.example.demo.reponses.payload.MessagePayload;
import com.example.demo.service.FoodMapper;
import com.example.demo.service.FoodService;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(path = "/food")
@SecurityRequirement(name = "Authorization")
@Api(tags = "Food", description = "Endpoint")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    FoodMapper foodMapper;

    @Operation(summary = "Get all Foods from your fridge", description = "You can get all the foods you have saved in your fridge")
    @GetMapping
    public ResponseEntity getFood(Authentication authentication) {
        List<Food> listFood = foodService.getFoods(authentication.getName());
        ListResponse foodResponse = new ListResponse(listFood);

        return ResponseEntity.status(200).body(foodResponse);
    }

    @Operation(summary = "Get the food with his barCode", description = "")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Food is correctly getting", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FoodDTO.class))),
            @ApiResponse(responseCode = "404", description = "Food is not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessagePayload.class))),
    })
    @GetMapping("/{bareCode}")
    public ResponseEntity<?> getFoodWithBareCode(@PathVariable("bareCode") String bareCode) {
        try {
            return ResponseEntity.status(200).body(foodMapper.toFoodDTO(foodService.getFood(bareCode)));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new MessagePayload("The food is not found"));
        }
    }

    @Operation(summary = "Add new food with bareCode", description = "Give a bareCode of the food to add it")
    @PostMapping("/{bareCode}")
    public ResponseEntity addFood(@PathVariable("bareCode") String bareCode) {
        try {
            foodService.save(new Food(bareCode));
            MessagePayload messagePayload = new MessagePayload("Food saved");
            return ResponseEntity.status(201).body(messagePayload);
        } catch (AlreadySavedError e) {
            return ResponseEntity.status(409).body(new MessagePayload("This food is already created"));
        }
    }

    @Operation(summary = "Add new food with bareCode", description = "Give a bareCode of the food to add it")
    @PutMapping("/{bareCode}")
    public ResponseEntity<MessagePayload> updateFood(@PathVariable("bareCode") String bareCode,
            @RequestBody FoodUpdateRequest foodUpdateRequest) {
        try {
            foodService.updateFoodInformations(bareCode, foodUpdateRequest);
            return ResponseEntity.status(200).body(new MessagePayload("The food is correctly modified"));
        } catch (NotFoundError e) {
            return ResponseEntity.status(404).body(new MessagePayload("The food is not found"));
        }

    }

    @Operation(summary = "Delete food", description = "Give a bareCode to delete the food")
    @DeleteMapping("/{bareCode}")
    public ResponseEntity deleteFood(@PathVariable String bareCode) {
        foodService.deleteFood(bareCode);
        return ResponseEntity.status(200).body("");
    }
}
