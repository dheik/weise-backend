package com.weise.api.service;

import com.weise.api.dto.request.TransactionCreateDTO;
import com.weise.api.model.Transaction;
import com.weise.api.model.User;
import com.weise.api.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;

    public Transaction create(TransactionCreateDTO data, User user) {
        Transaction transaction = new Transaction();
        transaction.setDescription(data.description());
        transaction.setAmount(data.amount());
        transaction.setDate(data.date());
        transaction.setType(data.type());

        transaction.setUser(user);

        return repository.save(transaction);
    }

    public List<Transaction> listAll(User user) {
        // Garante que o usuário só veja as transações DELE
        return repository.findAllByUserOrderByDateDesc(user);
    }
}