package com.bjam.guardian.controller;

import com.bjam.guardian.dto.FraudReportDTO;
import com.bjam.guardian.service.FraudReportService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fraud-reports")
public class FraudReportController {

    private final FraudReportService fraudService;

    public FraudReportController(FraudReportService fraudService) { this.fraudService = fraudService; }

    @PostMapping
    public ResponseEntity<FraudReportDTO> create(@Valid @RequestBody FraudReportDTO dto) {
        FraudReportDTO saved = fraudService.create(dto);
        return ResponseEntity.status(201).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FraudReportDTO> getById(@PathVariable Long id) {
        // No service não temos getById DTO, mas você pode implementar; por enquanto list+filter:
        return ResponseEntity.ok(fraudService.findAll().stream().filter(r -> r.getId().equals(id)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("FraudReport not found with id " + id)));
    }

    @GetMapping
    public ResponseEntity<List<FraudReportDTO>> list() {
        return ResponseEntity.ok(fraudService.findAll());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id, @RequestParam("status") String status,
                                             @RequestParam(value = "comment", required = false) String comment) {
        fraudService.updateStatus(id, com.bjam.guardian.model.FraudReport.Status.valueOf(status), comment);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        fraudService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
