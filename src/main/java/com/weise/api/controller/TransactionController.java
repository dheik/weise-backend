package com.weise.api.controller;

import com.weise.api.dto.request.TransactionCreateDTO;
import com.weise.api.model.Transaction;
import com.weise.api.model.User;
import com.weise.api.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;

    /**
     * 1. READ (Listar com Filtros)
     * Permite filtrar por intervalo de datas.
     * Exemplo: GET /transactions?startDate=2025-11-01&endDate=2025-11-30
     * Se não passar datas, traz tudo.
     */
    @GetMapping
    public ResponseEntity<List<Transaction>> list(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ) {
        List<Transaction> transactions = service.listFiltered(user, startDate, endDate);
        return ResponseEntity.ok(transactions);
    }

    /**
     * 2. CREATE (Criar Nova)
     * Recebe o JSON com descrição, valor, data, tipo e categoryId.
     */
    @PostMapping
    public ResponseEntity<Transaction> create(
            @RequestBody @Valid TransactionCreateDTO data,
            @AuthenticationPrincipal User user
    ) {
        Transaction newTransaction = service.create(data, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTransaction);
    }

    /**
     * 3. UPDATE (Atualizar Existente)
     * Recebe o ID na URL e os dados novos no Body.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Transaction> update(
            @PathVariable UUID id,
            @RequestBody @Valid TransactionCreateDTO data,
            @AuthenticationPrincipal User user
    ) {
        Transaction updatedTransaction = service.update(id, data, user);
        return ResponseEntity.ok(updatedTransaction);
    }

    /**
     * 4. DELETE (Remover)
     * Recebe o ID na URL e apaga do banco.
     * Retorna 204 No Content (padrão REST para deleção com sucesso).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id,
            @AuthenticationPrincipal User user
    ) {
        service.delete(id, user);
        return ResponseEntity.noContent().build();
    }
}