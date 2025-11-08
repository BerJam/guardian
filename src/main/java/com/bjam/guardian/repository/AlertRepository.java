package com.bjam.guardian.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bjam.guardian.model.Alert;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {

    // Antes: List<Alert> findByRead(boolean read);
    // Agora: pesquisa pelo nome da propriedade 'isRead' -> use 'IsRead' no método
    List<Alert> findByIsRead(boolean isRead);

    // outros métodos customizados, se houver
}