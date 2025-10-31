package com.bjam.guardian.util;

import com.bjam.guardian.dto.AccountDTO;
import com.bjam.guardian.dto.FraudReportDTO;
import com.bjam.guardian.dto.PixKeyDTO;
import com.bjam.guardian.dto.TransactionDTO;
import com.bjam.guardian.dto.TransactionResponseDTO;
import com.bjam.guardian.dto.UserResponseDTO;
import com.bjam.guardian.model.Account;
import com.bjam.guardian.model.FraudReport;
import com.bjam.guardian.model.PixKey;
import com.bjam.guardian.model.Transaction;
import com.bjam.guardian.model.User;

public class DTOMapper {

    /* USER */
    public static User toUserEntity(com.bjam.guardian.dto.UserCreateDTO dto) {
        User u = new User();
        u.setName(dto.getName());
        u.setEmail(dto.getEmail());
        // NÃO setar passwordHash aqui - o service deve hash
        return u;
    }

    public static UserResponseDTO toUserResponse(User u) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(u.getId());
        dto.setName(u.getName());
        dto.setEmail(u.getEmail());
        dto.setRole(u.getRole() != null ? u.getRole().name() : null);
        dto.setCreatedAt(u.getCreatedAt());
        return dto;
    }

    /* ACCOUNT */
    public static Account toAccountEntity(AccountDTO dto, User user) {
        Account a = new Account();
        a.setUser(user);
        a.setAccountNumber(dto.getAccountNumber());
        a.setBankName(dto.getBankName());
        a.setBalance(dto.getBalance() != null ? dto.getBalance() : java.math.BigDecimal.ZERO);
        return a;
    }

    public static AccountDTO toAccountDTO(Account a) {
        AccountDTO dto = new AccountDTO();
        dto.setId(a.getId());
        dto.setUserId(a.getUser() != null ? a.getUser().getId() : null);
        dto.setAccountNumber(a.getAccountNumber());
        dto.setBankName(a.getBankName());
        dto.setBalance(a.getBalance());
        return dto;
    }

    /* PIXKEY */
    public static PixKey toPixKeyEntity(PixKeyDTO dto, Account account) {
        PixKey p = new PixKey();
        p.setKeyValue(dto.getKeyValue());
        p.setAccount(account);
        p.setKeyType(PixKey.KeyType.valueOf(dto.getKeyType()));
        return p;
    }

    public static PixKeyDTO toPixKeyDTO(PixKey p) {
        PixKeyDTO dto = new PixKeyDTO();
        dto.setId(p.getId());
        dto.setKeyValue(p.getKeyValue());
        dto.setKeyType(p.getKeyType().name());
        dto.setAccountId(p.getAccount() != null ? p.getAccount().getId() : null);
        return dto;
    }

    /* TRANSACTION */
    public static Transaction toTransactionEntity(TransactionDTO dto, Account origin, PixKey target) {
        Transaction t = new Transaction();
        t.setOriginAccount(origin);
        t.setTargetPixKey(target);
        t.setAmount(dto.getAmount());
        t.setDescription(dto.getDescription());
        t.setSimulated(dto.getSimulated() != null ? dto.getSimulated() : false);
        return t;
    }

    public static TransactionResponseDTO toTransactionResponse(Transaction t) {
        TransactionResponseDTO dto = new TransactionResponseDTO();
        dto.setId(t.getId());
        dto.setOriginAccountId(t.getOriginAccount() != null ? t.getOriginAccount().getId() : null);
        dto.setTargetPixKeyId(t.getTargetPixKey() != null ? t.getTargetPixKey().getId() : null);
        dto.setAmount(t.getAmount());
        dto.setStatus(t.getStatus() != null ? t.getStatus().name() : null);
        dto.setTimestamp(t.getTimestamp());
        dto.setDescription(t.getDescription());
        dto.setSimulated(t.isSimulated());
        return dto;
    }

    /* FRAUDREPORT */
    public static FraudReport toFraudReportEntity(FraudReportDTO dto, User reporter, Transaction tx) {
        FraudReport fr = new FraudReport();
        fr.setReporter(reporter);
        fr.setTransaction(tx);
        fr.setDescription(dto.getDescription());
        return fr;
    }

    public static FraudReportDTO toFraudReportDTO(FraudReport fr) {
        FraudReportDTO dto = new FraudReportDTO();
        dto.setId(fr.getId());
        dto.setReporterId(fr.getReporter() != null ? fr.getReporter().getId() : null);
        dto.setTransactionId(fr.getTransaction() != null ? fr.getTransaction().getId() : null);
        dto.setDescription(fr.getDescription());
        return dto;
    }

    // Entidade -> DTO
    public static com.bjam.guardian.dto.AlertDTO toAlertDTO(com.bjam.guardian.model.Alert a) {
        com.bjam.guardian.dto.AlertDTO dto = new com.bjam.guardian.dto.AlertDTO();
        dto.setId(a.getId());
        dto.setRelatedTransactionId(a.getRelatedTransaction() != null ? a.getRelatedTransaction().getId() : null);
        dto.setLevel(a.getLevel() != null ? a.getLevel().name() : null);
        dto.setMessage(a.getMessage());
        dto.setRead(a.isRead()); // usa isRead() da entidade
        return dto;
    }

    // DTO -> Entidade
    public static com.bjam.guardian.model.Alert toAlertEntity(com.bjam.guardian.dto.AlertDTO dto, com.bjam.guardian.model.Transaction tx) {
        com.bjam.guardian.model.Alert a = new com.bjam.guardian.model.Alert();
        a.setRelatedTransaction(tx);
        if (dto.getLevel() != null) {
            a.setLevel(com.bjam.guardian.model.Alert.Level.valueOf(dto.getLevel()));
        }
        a.setMessage(dto.getMessage());
        a.setRead(dto.isRead()); // seta isRead da entidade a partir do DTO
        return a;
    }

}
