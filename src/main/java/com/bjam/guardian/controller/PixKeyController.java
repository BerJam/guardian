package com.bjam.guardian.controller;

import com.bjam.guardian.dto.PixKeyDTO;
import com.bjam.guardian.service.PixKeyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pix-keys")
public class PixKeyController {

    private final PixKeyService pixKeyService;

    public PixKeyController(PixKeyService pixKeyService) { this.pixKeyService = pixKeyService; }

    @PostMapping
    public ResponseEntity<PixKeyDTO> create(@Valid @RequestBody PixKeyDTO dto) {
        PixKeyDTO saved = pixKeyService.create(dto);
        return ResponseEntity.status(201).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PixKeyDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(pixKeyService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<PixKeyDTO>> list() {
        return ResponseEntity.ok(pixKeyService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PixKeyDTO> update(@PathVariable Long id, @Valid @RequestBody PixKeyDTO dto) {
        return ResponseEntity.ok(pixKeyService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pixKeyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
