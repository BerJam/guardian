package com.bjam.guardian.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bjam.guardian.dto.AlertDTO;
import com.bjam.guardian.service.AlertService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) { this.alertService = alertService; }

    @PostMapping
    public ResponseEntity<AlertDTO> create(@Valid @RequestBody AlertDTO dto) {
        AlertDTO saved = alertService.create(dto);
        return ResponseEntity.status(201).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlertDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(alertService.findAll().stream().filter(a -> a.getId().equals(id))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Alert not found with id " + id)));
    }

    @GetMapping
    public ResponseEntity<List<AlertDTO>> list() {
        return ResponseEntity.ok(alertService.findAll());
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
        alertService.markAsRead(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        alertService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
