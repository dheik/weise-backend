package com.weise.api.controller;

import com.weise.api.model.Category;
import com.weise.api.model.User;
import com.weise.api.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryRepository repository;

    @GetMapping
    public ResponseEntity<List<Category>> list(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(repository.findAllByUser(user));
    }
}