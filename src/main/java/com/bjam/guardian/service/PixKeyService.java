package com.bjam.guardian.service;

import com.bjam.guardian.dto.PixKeyDTO;
import com.bjam.guardian.exception.ResourceNotFoundException;
import com.bjam.guardian.model.Account;
import com.bjam.guardian.model.PixKey;
import com.bjam.guardian.repository.PixKeyRepository;
import com.bjam.guardian.util.DTOMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PixKeyService {

    private final PixKeyRepository pixRepo;
    private final AccountService accountService; // usar accountService para validar conta

    public PixKeyService(PixKeyRepository pixRepo, AccountService accountService) {
        this.pixRepo = pixRepo;
        this.accountService = accountService;
    }

    @Transactional
    public PixKeyDTO create(PixKeyDTO dto) {
        Account acc = accountService.findEntityById(dto.getAccountId());
        // validações simples: unicidade é garantida pelo DB
        PixKey p = DTOMapper.toPixKeyEntity(dto, acc);
        PixKey saved = pixRepo.save(p);
        return DTOMapper.toPixKeyDTO(saved);
    }

    @Transactional(readOnly = true)
    public PixKeyDTO getById(Long id) {
        PixKey p = pixRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PixKey not found with id " + id));
        return DTOMapper.toPixKeyDTO(p);
    }

    @Transactional(readOnly = true)
    public List<PixKeyDTO> findAll() {
        return pixRepo.findAll().stream()
                .map(DTOMapper::toPixKeyDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public PixKeyDTO update(Long id, PixKeyDTO dto) {
        PixKey p = pixRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PixKey not found with id " + id));
        if (dto.getKeyValue() != null) p.setKeyValue(dto.getKeyValue());
        if (dto.getKeyType() != null) p.setKeyType(PixKey.KeyType.valueOf(dto.getKeyType()));
        PixKey saved = pixRepo.save(p);
        return DTOMapper.toPixKeyDTO(saved);
    }

    @Transactional
    public void delete(Long id) {
        if (!pixRepo.existsById(id)) {
            throw new ResourceNotFoundException("PixKey not found with id " + id);
        }
        pixRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public PixKey findEntityById(Long id) {
        return pixRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PixKey not found with id " + id));
    }
}
