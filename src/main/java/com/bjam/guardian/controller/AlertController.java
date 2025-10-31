package com.bjam.guardian.controller;

import com.bjam.guardian.dto.AlertDTO;
import com.bjam.guardian.service.AlertService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
