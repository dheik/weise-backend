package com.weise.api.controller;

import com.weise.api.dto.request.TransactionCreateDTO;
import com.weise.api.model.Transaction;
import com.weise.api.model.User;
import com.weise.api.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;

    @PostMapping
    public ResponseEntity<Transaction> create(
            @RequestBody @Valid TransactionCreateDTO data,
            @AuthenticationPrincipal User user
    ) {
        Transaction newTransaction = service.create(data, user);
        return ResponseEntity.status(201).body(newTransaction);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> list(@AuthenticationPrincipal User user) {
        List<Transaction> transactions = service.listAll(user);
        return ResponseEntity.ok(transactions);
    }
}