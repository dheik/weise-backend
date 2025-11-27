package com.weise.api.repository;

import com.weise.api.model.Transaction;
import com.weise.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

        // Filtro Mágico: COALESCE evita o erro "could not determine data type" do
        // Postgres
        // Se startDate for null, usa t.date (t.date >= t.date é sempre true)
        @Query("SELECT t FROM Transaction t WHERE t.user = :user " +
                        "AND t.date >= COALESCE(:startDate, t.date) " +
                        "AND t.date <= COALESCE(:endDate, t.date) " +
                        "ORDER BY t.date DESC")
        List<Transaction> findFiltered(
                        @Param("user") User user,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate);
}