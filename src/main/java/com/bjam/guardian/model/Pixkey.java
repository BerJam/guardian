package com.bjam.guardian.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "pix_keys", uniqueConstraints = @UniqueConstraint(columnNames = "key_value"))
public class PixKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private KeyType keyType;

    @Column(name = "key_value", nullable = false)
    private String keyValue;

    // Chave pertence a uma conta
    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id")
    private Account account;

    private Instant createdAt = Instant.now();

    public PixKey() {}

    public PixKey(KeyType keyType, String keyValue, Account account) {
        this.keyType = keyType;
        this.keyValue = keyValue;
        this.account = account;
        this.createdAt = Instant.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public KeyType getKeyType() { return keyType; }
    public void setKeyType(KeyType keyType) { this.keyType = keyType; }

    public String getKeyValue() { return keyValue; }
    public void setKeyValue(String keyValue) { this.keyValue = keyValue; }

    public Account getAccount() { return account; }
    public void setAccount(Account account) { this.account = account; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public enum KeyType { CPF, EMAIL, PHONE, RANDOM }
}
