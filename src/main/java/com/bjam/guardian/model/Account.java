package com.bjam.guardian.model;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Conta pertence a um usuário
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    private String accountNumber;
    private String bankName;

    // Saldo
    private BigDecimal balance = BigDecimal.ZERO;

    private Instant createdAt = Instant.now();

    public Account() {}

    public Account(User user, String accountNumber, String bankName, BigDecimal balance) {
        this.user = user;
        this.accountNumber = accountNumber;
        this.bankName = bankName;
        this.balance = balance == null ? BigDecimal.ZERO : balance;
        this.createdAt = Instant.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
