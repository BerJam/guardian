package com.bjam.guardian.service;

import com.bjam.guardian.dto.AccountDTO;
import com.bjam.guardian.exception.ResourceNotFoundException;
import com.bjam.guardian.model.Account;
import com.bjam.guardian.model.User;
import com.bjam.guardian.repository.AccountRepository;
import com.bjam.guardian.repository.UserRepository;
import com.bjam.guardian.util.DTOMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final AccountRepository accountRepo;
    private final UserRepository userRepo;

    public AccountService(AccountRepository accountRepo, UserRepository userRepo) {
        this.accountRepo = accountRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    public AccountDTO create(AccountDTO dto) {
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + dto.getUserId()));
        Account a = DTOMapper.toAccountEntity(dto, user);
        Account saved = accountRepo.save(a);
        return DTOMapper.toAccountDTO(saved);
    }

    @Transactional(readOnly = true)
    public AccountDTO getById(Long id) {
        Account a = accountRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id " + id));
        return DTOMapper.toAccountDTO(a);
    }

    @Transactional(readOnly = true)
    public List<AccountDTO> findAll() {
        return accountRepo.findAll().stream()
                .map(DTOMapper::toAccountDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public AccountDTO update(Long id, AccountDTO dto) {
        Account a = accountRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id " + id));
        if (dto.getAccountNumber() != null) a.setAccountNumber(dto.getAccountNumber());
        if (dto.getBankName() != null) a.setBankName(dto.getBankName());
        if (dto.getBalance() != null) a.setBalance(dto.getBalance());
        Account saved = accountRepo.save(a);
        return DTOMapper.toAccountDTO(saved);
    }

    @Transactional
    public void delete(Long id) {
        if (!accountRepo.existsById(id)) {
            throw new ResourceNotFoundException("Account not found with id " + id);
        }
        accountRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Account findEntityById(Long id) {
        return accountRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id " + id));
    }
}
