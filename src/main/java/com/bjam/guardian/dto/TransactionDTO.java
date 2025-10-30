package com.bjam.guardian.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TransactionDTO {

    @NotNull(message = "O ID do usuário é obrigatório")
    private Long userId;

    @NotBlank(message = "O tipo de transação é obrigatório")
    private String type;

    @NotNull(message = "O valor é obrigatório")
    @Min(value = 1, message = "O valor mínimo é 1")
    private Double amount;

    // Getters e Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
}
