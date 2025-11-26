package com.weise.api.dto.request;

import com.weise.api.model.enums.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionCreateDTO(
        @NotBlank(message = "Descrição é obrigatória")
        String description,

        @NotNull(message = "Valor é obrigatório")
        @DecimalMin(value = "0.01", message = "O valor deve ser positivo")
        BigDecimal amount,

        @NotNull(message = "Data é obrigatória")
        LocalDate date,

        @NotNull(message = "Tipo é obrigatório (INCOME ou EXPENSE)")
        TransactionType type
) {}