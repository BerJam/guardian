package com.bjam.guardian.model;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Conta de origem (quem envia)
    @ManyToOne(optional = false)
    @JoinColumn(name = "origin_account_id")
    private Account originAccount;

    // Target: chave Pix
    @ManyToOne(optional = false)
    @JoinColumn(name = "target_pix_key_id")
    private PixKey targetPixKey;

    private BigDecimal amount;

    private Instant timestamp = Instant.now();

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    private boolean simulated = false;

    @Column(length = 1000)
    private String description;

    public Transaction() {}

    public Transaction(Account originAccount, PixKey targetPixKey, BigDecimal amount, boolean simulated, String description) {
        this.originAccount = originAccount;
        this.targetPixKey = targetPixKey;
        this.amount = amount;
        this.simulated = simulated;
        this.description = description;
        this.timestamp = Instant.now();
        this.status = Status.PENDING;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Account getOriginAccount() { return originAccount; }
    public void setOriginAccount(Account originAccount) { this.originAccount = originAccount; }

    public PixKey getTargetPixKey() { return targetPixKey; }
    public void setTargetPixKey(PixKey targetPixKey) { this.targetPixKey = targetPixKey; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public boolean isSimulated() { return simulated; }
    public void setSimulated(boolean simulated) { this.simulated = simulated; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public enum Status { PENDING, COMPLETED, REVERSED }
}
