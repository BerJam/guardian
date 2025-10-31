package com.bjam.guardian.service;

import com.bjam.guardian.dto.TransactionDTO;
import com.bjam.guardian.exception.ResourceNotFoundException;
import com.bjam.guardian.model.Account;
import com.bjam.guardian.model.PixKey;
import com.bjam.guardian.model.Transaction;
import com.bjam.guardian.repository.TransactionRepository;
import com.bjam.guardian.util.DTOMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository txRepo;
    private final AccountService accountService;
    private final PixKeyService pixKeyService;
    private final FraudDetectionService fraudDetectionService;

    public TransactionService(TransactionRepository txRepo,
                              AccountService accountService,
                              PixKeyService pixKeyService,
                              FraudDetectionService fraudDetectionService) {
        this.txRepo = txRepo;
        this.accountService = accountService;
        this.pixKeyService = pixKeyService;
        this.fraudDetectionService = fraudDetectionService;
    }

    @Transactional
    public Transaction createFromDto(TransactionDTO dto) {
        Account origin = accountService.findEntityById(dto.getOriginAccountId());
        PixKey target = pixKeyService.findEntityById(dto.getTargetPixKeyId());

        Transaction tx = DTOMapper.toTransactionEntity(dto, origin, target);

        // regras simples: saldo (apenas se não simulated)
        if (!tx.isSimulated()) {
            if (origin.getBalance().compareTo(tx.getAmount()) < 0) {
                throw new IllegalArgumentException("Saldo insuficiente");
            }
            // debita origem e credita destino
            origin.setBalance(origin.getBalance().subtract(tx.getAmount()));
            accountService.update(origin.getId(), DTOMapper.toAccountDTO(origin)); // reuse update
            Account targetAccount = target.getAccount();
            targetAccount.setBalance(targetAccount.getBalance().add(tx.getAmount()));
            accountService.update(targetAccount.getId(), DTOMapper.toAccountDTO(targetAccount));
            tx.setStatus(Transaction.Status.COMPLETED);
        } else {
            tx.setStatus(Transaction.Status.PENDING);
        }

        Transaction saved = txRepo.save(tx);

        // chama detector (assíncrono seria ideal, mas executamos aqui)
        fraudDetectionService.analyze(saved);

        return saved;
    }

    @Transactional(readOnly = true)
    public Transaction findById(Long id) {
        return txRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id " + id));
    }

    @Transactional(readOnly = true)
    public List<Transaction> findAll() {
        return txRepo.findAll(Sort.by(Sort.Direction.DESC, "timestamp"));
    }

    @Transactional
    public void delete(Long id) {
        if (!txRepo.existsById(id)) {
            throw new ResourceNotFoundException("Transaction not found with id " + id);
        }
        txRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Transaction> findRecentByOriginAccountId(Long accountId) {
        // usar método findTopN se implementado ou query por timestamp
        // aqui usamos repository helper that returns ordered list (we defined findRecentByOriginAccountId)
        return txRepo.findRecentByOriginAccountId(accountId);
    }
}
