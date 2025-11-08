package com.bjam.guardian.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bjam.guardian.model.Alert;
import com.bjam.guardian.model.PixKey;
import com.bjam.guardian.model.Transaction;

@Service
public class FraudDetectionService {

    private final AlertService alertService;
    private final TransactionService transactionService;

    public FraudDetectionService(@Lazy AlertService alertService, @Lazy TransactionService transactionService) {
        this.alertService = alertService;
        this.transactionService = transactionService;
    }

    /**
     * Analisa uma transação e cria alertas caso alguma regra dispare.
     * Regras implementadas:
     *  - Valor alto (> 5000) -> WARNING
     *  - Target pix key criada há menos de 24 horas -> WARNING
     *  - Mais de 3 transações de origem no último 1 minuto -> CRITICAL
     */
    @Transactional
    public void analyze(Transaction tx) {
        // regra 1: valor alto
        BigDecimal threshold = new BigDecimal("5000");
        if (tx.getAmount() != null && tx.getAmount().compareTo(threshold) > 0) {
            alertService.createForTransaction(tx.getId(), Alert.Level.WARNING.name(),
                    "Transação de alto valor: " + tx.getAmount());
        }

        // regra 2: target pix key criada há menos de 24h
        PixKey target = tx.getTargetPixKey();
        if (target != null && target.getCreatedAt() != null) {
            Instant created = target.getCreatedAt();
            if (Duration.between(created, Instant.now()).toHours() < 24) {
                alertService.createForTransaction(tx.getId(), Alert.Level.WARNING.name(),
                        "Chave PIX destino criada há menos de 24 horas");
            }
        }

        // regra 3: frequência - >3 transações da mesma origem em 1 minuto
        List<Transaction> recent = transactionService.findRecentByOriginAccountId(tx.getOriginAccount().getId());
        long countInLastMinute = recent.stream()
                .filter(t -> t.getTimestamp() != null && Duration.between(t.getTimestamp(), Instant.now()).toMinutes() < 1)
                .count();
        if (countInLastMinute >= 3) {
            alertService.createForTransaction(tx.getId(), Alert.Level.CRITICAL.name(),
                    "Múltiplas transações em curto período (>=3 em 1 minuto)");
        }
    }
}
