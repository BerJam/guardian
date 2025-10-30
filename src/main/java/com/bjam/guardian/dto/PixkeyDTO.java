package com.bjam.guardian.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PixKeyDTO {

    private Long id;

    @NotBlank(message = "keyValue é obrigatório")
    private String keyValue;

    @NotNull(message = "keyType é obrigatório")
    private String keyType; // CPF, EMAIL, PHONE, RANDOM

    @NotNull(message = "accountId é obrigatório")
    private Long accountId;

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getKeyValue() { return keyValue; }
    public void setKeyValue(String keyValue) { this.keyValue = keyValue; }
    public String getKeyType() { return keyType; }
    public void setKeyType(String keyType) { this.keyType = keyType; }
    public Long getAccountId() { return accountId; }
    public void setAccountId(Long accountId) { this.accountId = accountId; }
}
