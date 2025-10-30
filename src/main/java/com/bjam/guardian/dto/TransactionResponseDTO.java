package com.bjam.guardian.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class TransactionResponseDTO {
    private Long id;
    private Long originAccountId;
    private Long targetPixKeyId;
    private BigDecimal amount;
    private String status;
    private Instant timestamp;
    private String description;
    private boolean simulated;

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getOriginAccountId() { return originAccountId; }
    public void setOriginAccountId(Long originAccountId) { this.originAccountId = originAccountId; }
    public Long getTargetPixKeyId() { return targetPixKeyId; }
    public void setTargetPixKeyId(Long targetPixKeyId) { this.targetPixKeyId = targetPixKeyId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isSimulated() { return simulated; }
    public void setSimulated(boolean simulated) { this.simulated = simulated; }
}
