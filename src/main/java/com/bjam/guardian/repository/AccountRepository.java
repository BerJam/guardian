package com.bjam.guardian.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bjam.guardian.model.Account;
import com.bjam.guardian.model.User;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    // buscar contas de um usuário
    List<Account> findByUser(User user);

    // buscar contas por accountNumber (ex.: para validação)
    List<Account> findByAccountNumber(String accountNumber);
}
