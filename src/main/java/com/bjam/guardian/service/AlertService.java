package com.bjam.guardian.service;

import com.bjam.guardian.dto.AlertDTO;
import com.bjam.guardian.exception.ResourceNotFoundException;
import com.bjam.guardian.model.Alert;
import com.bjam.guardian.model.Transaction;
import com.bjam.guardian.repository.AlertRepository;
import com.bjam.guardian.util.DTOMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlertService {

    private final AlertRepository alertRepo;
    private final TransactionService transactionService;

    public AlertService(AlertRepository alertRepo, TransactionService transactionService) {
        this.alertRepo = alertRepo;
        this.transactionService = transactionService;
    }

    @Transactional
    public AlertDTO createForTransaction(Long txId, String level, String message) {
        Transaction tx = transactionService.findById(txId);
        Alert a = new Alert(tx, Alert.Level.valueOf(level), message);
        Alert saved = alertRepo.save(a);
        return DTOMapper.toAlertDTO(saved);
    }

    @Transactional
    public AlertDTO create(AlertDTO dto) {
        Transaction tx = null;
        if (dto.getRelatedTransactionId() != null) {
            tx = transactionService.findById(dto.getRelatedTransactionId());
        }
        Alert a = DTOMapper.toAlertEntity(dto, tx);
        Alert saved = alertRepo.save(a);
        return DTOMapper.toAlertDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<AlertDTO> findAll() {
        return alertRepo.findAll().stream().map(DTOMapper::toAlertDTO).collect(Collectors.toList());
    }

    @Transactional
    public void markAsRead(Long id) {
        Alert a = alertRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alert not found with id " + id));
        a.setRead(true);
        alertRepo.save(a);
    }

    @Transactional
    public void delete(Long id) {
        if (!alertRepo.existsById(id)) {
            throw new ResourceNotFoundException("Alert not found with id " + id);
        }
        alertRepo.deleteById(id);
    }
}
