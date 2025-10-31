package com.bjam.guardian.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bjam.guardian.dto.FraudReportDTO;
import com.bjam.guardian.exception.ResourceNotFoundException;
import com.bjam.guardian.model.FraudReport;
import com.bjam.guardian.model.Transaction;
import com.bjam.guardian.model.User;
import com.bjam.guardian.repository.FraudReportRepository;
import com.bjam.guardian.util.DTOMapper;

@Service
public class FraudReportService {

    private final FraudReportRepository fraudRepo;
    private final UserService userService;
    private final TransactionService txService;

    public FraudReportService(FraudReportRepository fraudRepo, UserService userService, TransactionService txService) {
        this.fraudRepo = fraudRepo;
        this.userService = userService;
        this.txService = txService;
    }

    @Transactional
    public FraudReportDTO create(FraudReportDTO dto) {
        User reporter = null;
        Transaction tx = null;
        if (dto.getReporterId() != null) reporter = userService.findEntityById(dto.getReporterId());
        if (dto.getTransactionId() != null) tx = txService.findById(dto.getTransactionId());
        FraudReport fr = DTOMapper.toFraudReportEntity(dto, reporter, tx);
        FraudReport saved = fraudRepo.save(fr);
        return DTOMapper.toFraudReportDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<FraudReportDTO> findAll() {
        return fraudRepo.findAll().stream().map(DTOMapper::toFraudReportDTO).collect(Collectors.toList());
    }

    @Transactional
    public void updateStatus(Long id, FraudReport.Status status, String adminComment) {
        FraudReport fr = fraudRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FraudReport not found with id " + id));
        fr.setStatus(status);
        fr.setAdminComment(adminComment);
        fraudRepo.save(fr);
    }

    @Transactional
    public void delete(Long id) {
        if (!fraudRepo.existsById(id)) {
            throw new ResourceNotFoundException("FraudReport not found with id " + id);
        }
        fraudRepo.deleteById(id);
    }
}
