package com.bjam.guardian.dto;

import jakarta.validation.constraints.NotBlank;

public class FraudReportDTO {

    private Long id;

    private Long reporterId; // opcional, pode ser null

    private Long transactionId; // opcional

    @NotBlank(message = "description é obrigatório")
    private String description;

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getReporterId() { return reporterId; }
    public void setReporterId(Long reporterId) { this.reporterId = reporterId; }
    public Long getTransactionId() { return transactionId; }
    public void setTransactionId(Long transactionId) { this.transactionId = transactionId; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
