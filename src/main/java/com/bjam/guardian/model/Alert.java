package com.bjam.guardian.model;

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
@Table(name = "alerts")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relaciona a transação que gerou o alerta (pode ser null em alguns casos)
    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction relatedTransaction;

    @Enumerated(EnumType.STRING)
    private Level level;

    @Column(length = 1000)
    private String message;

    private Instant createdAt = Instant.now();

    private boolean read = false;

    public Alert() {}

    public Alert(Transaction relatedTransaction, Level level, String message) {
        this.relatedTransaction = relatedTransaction;
        this.level = level;
        this.message = message;
        this.createdAt = Instant.now();
        this.read = false;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Transaction getRelatedTransaction() { return relatedTransaction; }
    public void setRelatedTransaction(Transaction relatedTransaction) { this.relatedTransaction = relatedTransaction; }

    public Level getLevel() { return level; }
    public void setLevel(Level level) { this.level = level; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public boolean isRead() { return read; }
    public void setRead(boolean read) { this.read = read; }

    public enum Level { INFO, WARNING, CRITICAL }
}
