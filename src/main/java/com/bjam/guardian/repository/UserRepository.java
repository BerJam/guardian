package com.bjam.guardian.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bjam.guardian.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // busca por email (útil para login / verificar duplicidade)
    Optional<User> findByEmail(String email);

    // existe usuário com esse email?
    boolean existsByEmail(String email);
}
