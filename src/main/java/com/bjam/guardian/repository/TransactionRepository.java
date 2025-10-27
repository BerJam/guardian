package com.bjam.guardian.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bjam.guardian.model.Account;
import com.bjam.guardian.model.PixKey;
import com.bjam.guardian.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // transações por conta de origem
    List<Transaction> findByOriginAccount(Account account);

    List<Transaction> findByOriginAccountId(Long accountId);

    // transações que usam uma determinada chave PIX como destino
    List<Transaction> findByTargetPixKey(PixKey pixKey);

    // buscar por intervalo de tempo
    List<Transaction> findByTimestampBetween(Instant start, Instant end);

    // exemplo custom: últimas N transações de uma conta (native query / limit não padrão em JPQL)
    @Query(value = "SELECT t FROM Transaction t WHERE t.originAccount.id = :accountId ORDER BY t.timestamp DESC")
    List<Transaction> findRecentByOriginAccountId(Long accountId);
}
