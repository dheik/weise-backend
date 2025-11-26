package com.weise.api.repository;

import com.weise.api.model.Transaction;
import com.weise.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    // busca todas as transações de um usuário específico, ordenadas por data (mais recente primeiro)
    List<Transaction> findAllByUserOrderByDateDesc(User user);
}