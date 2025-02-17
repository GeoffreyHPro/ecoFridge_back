package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.payload.EmailPasswordRequest;
import com.example.demo.reponses.TokenResponse;
import com.example.demo.reponses.payload.MessagePayload;
import com.example.demo.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(path = "/auth")
@Api(tags = "Auth", description = "Endpoint")
public class AuthController {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Operation(summary = "Connection with your user account", description = "You get a token and user role when you signIn correctly")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponse.class))),
            @ApiResponse(responseCode = "401", description = "Wrong login parameters", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessagePayload.class)))
    })

    @PostMapping(path = "/signIn")
    public ResponseEntity<?> authenticationUser(
            @RequestBody EmailPasswordRequest content) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    content.getEmail(), content.getPassword()));

            TokenResponse tR = userService.getTokenResponse(content);

            return ResponseEntity.status(200).body(tR);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new MessagePayload("Wrong mail/password"));
        }

    }

    @Operation(summary = "Create new user", description = "Create new user with unique email and a password")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Account created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessagePayload.class))),
            @ApiResponse(responseCode = "400", description = "Parameter/s is/are missing", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessagePayload.class))),
            @ApiResponse(responseCode = "409", description = "This account is already exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessagePayload.class)))
    })
    @PostMapping(path = "/signUp")
    public ResponseEntity<MessagePayload> get(@RequestBody EmailPasswordRequest content) {

        if (content.getEmail().isEmpty()) {
            return ResponseEntity.status(400).body(new MessagePayload("Please give an email"));
        }
        if (content.getPassword().isEmpty()) {
            return ResponseEntity.status(400).body(new MessagePayload("Please give a password"));
        }
        try {
            userService.save(new User(content.getEmail(), passwordEncoder.encode(content.getPassword())));
            return ResponseEntity.status(201).body(new MessagePayload("Your account is created"));
        } catch (Exception e) {
            return ResponseEntity.status(409).body(new MessagePayload("This account already exist"));
        }
    }
}