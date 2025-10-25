package com.bjam.guardian.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class TransactionDTO {
    @NotNull
    private Long originAccountId;

    @NotNull
    private Long targetPixKeyId;

    @NotNull @DecimalMin("0.01")
    private BigDecimal amount;

    private String description;
    // getters/setters
}
