package com.bjam.guardian.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bjam.guardian.model.Account;
import com.bjam.guardian.model.PixKey;

@Repository
public interface PixKeyRepository extends JpaRepository<PixKey, Long> {
    // buscar chave por valor exato (útil em rotas que recebem a chave destino)
    Optional<PixKey> findByKeyValue(String keyValue);

    // listar chaves de uma conta
    List<PixKey> findByAccount(Account account);

    // existe chave com esse valor?
    boolean existsByKeyValue(String keyValue);
}
