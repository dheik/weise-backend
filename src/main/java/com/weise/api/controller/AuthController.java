package com.weise.api.controller;

import com.weise.api.dto.request.UserCreateDTO;
import com.weise.api.model.User;
import com.weise.api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserCreateDTO data) {
        User user = userService.registerUser(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}