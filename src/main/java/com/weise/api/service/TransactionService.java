package com.weise.api.service;

import com.weise.api.dto.request.TransactionCreateDTO;
import com.weise.api.model.Transaction;
import com.weise.api.model.User;
import com.weise.api.repository.CategoryRepository;
import com.weise.api.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.weise.api.model.Category;
import jakarta.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;
    private final CategoryRepository categoryRepository; // Injetar repo de categorias

    // CREATE (Atualizado com Categoria)
    public Transaction create(TransactionCreateDTO data, User user) {
        Transaction t = new Transaction();
        t.setDescription(data.description());
        t.setAmount(data.amount());
        t.setDate(data.date());
        t.setType(data.type());
        t.setUser(user);

        // Buscar categoria se vier o ID
        if (data.categoryId() != null) {
            Category c = categoryRepository.findById(data.categoryId())
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
            t.setCategory(c);
        }

        return repository.save(t);
    }

    // READ (Com Filtros)
    public List<Transaction> listFiltered(User user, LocalDate start, LocalDate end) {
        return repository.findFiltered(user, start, end);
    }

    // UPDATE
    public Transaction update(UUID id, TransactionCreateDTO data, User user) {
        Transaction t = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transação não encontrada"));

        // Segurança: Verificar se a transação é mesmo desse usuário
        if (!t.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Acesso negado");
        }

        t.setDescription(data.description());
        t.setAmount(data.amount());
        t.setDate(data.date());
        t.setType(data.type());

        if (data.categoryId() != null) {
            Category c = categoryRepository.findById(data.categoryId())
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
            t.setCategory(c);
        }

        return repository.save(t);
    }

    // DELETE
    public void delete(UUID id, User user) {
        Transaction t = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transação não encontrada"));

        if (!t.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Acesso negado");
        }
        repository.delete(t);
    }
}