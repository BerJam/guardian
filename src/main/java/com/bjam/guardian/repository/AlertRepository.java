package com.bjam.guardian.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bjam.guardian.model.Alert;
import com.bjam.guardian.model.Transaction;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findByRead(boolean read);
    List<Alert> findByRelatedTransaction(Transaction tx);
    List<Alert> findByRelatedTransactionId(Long txId);
}
