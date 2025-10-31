package com.bjam.guardian.controller;

import com.bjam.guardian.dto.TransactionDTO;
import com.bjam.guardian.dto.TransactionResponseDTO;
import com.bjam.guardian.model.Transaction;
import com.bjam.guardian.service.TransactionService;
import com.bjam.guardian.util.DTOMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService txService;

    public TransactionController(TransactionService txService) { this.txService = txService; }

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> create(@Valid @RequestBody TransactionDTO dto) {
        Transaction saved = txService.createFromDto(dto);
        return ResponseEntity.status(201).body(DTOMapper.toTransactionResponse(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> getById(@PathVariable Long id) {
        Transaction t = txService.findById(id);
        return ResponseEntity.ok(DTOMapper.toTransactionResponse(t));
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> list() {
        List<Transaction> all = txService.findAll();
        List<TransactionResponseDTO> out = all.stream().map(DTOMapper::toTransactionResponse).collect(Collectors.toList());
        return ResponseEntity.ok(out);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        txService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
