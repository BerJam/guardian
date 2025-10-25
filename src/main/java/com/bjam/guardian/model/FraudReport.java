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
@Table(name = "fraud_reports")
public class FraudReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Quem reportou (pode ser null para reports anônimos ou gerais)
    @ManyToOne
    @JoinColumn(name = "reporter_id")
    private User reporter;

    // Transação relacionada (pode ser null se for denúncia geral)
    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    @Column(length = 2000)
    private String description;

    private Instant reportDate = Instant.now();

    @Enumerated(EnumType.STRING)
    private Status status = Status.NEW;

    @Column(length = 2000)
    private String adminComment;

    public FraudReport() {}

    public FraudReport(User reporter, Transaction transaction, String description) {
        this.reporter = reporter;
        this.transaction = transaction;
        this.description = description;
        this.reportDate = Instant.now();
        this.status = Status.NEW;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getReporter() { return reporter; }
    public void setReporter(User reporter) { this.reporter = reporter; }

    public Transaction getTransaction() { return transaction; }
    public void setTransaction(Transaction transaction) { this.transaction = transaction; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Instant getReportDate() { return reportDate; }
    public void setReportDate(Instant reportDate) { this.reportDate = reportDate; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public String getAdminComment() { return adminComment; }
    public void setAdminComment(String adminComment) { this.adminComment = adminComment; }

    public enum Status { NEW, INVESTIGATING, RESOLVED }
}
