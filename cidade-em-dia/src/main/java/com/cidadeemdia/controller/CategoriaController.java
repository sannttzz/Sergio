package com.cidadeemdia.controller;

import com.cidadeemdia.dto.CategoriaDTO;
import com.cidadeemdia.entity.Categoria;
import com.cidadeemdia.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categorias")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CategoriaController {
    
    @Autowired
    private CategoriaService categoriaService;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENDENTE')")
    public ResponseEntity<Categoria> criar(@Valid @RequestBody CategoriaDTO dto) {
        Categoria categoria = categoriaService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENDENTE', 'CIDADAO')")
    public ResponseEntity<Categoria> obterPorId(@PathVariable Long id) {
        Categoria categoria = categoriaService.obterPorId(id);
        return ResponseEntity.ok(categoria);
    }
    
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENDENTE', 'CIDADAO')")
    public ResponseEntity<List<Categoria>> listar() {
        List<Categoria> categorias = categoriaService.listar();
        return ResponseEntity.ok(categorias);
    }
    
    @GetMapping("/ativas")
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENDENTE', 'CIDADAO')")
    public ResponseEntity<List<Categoria>> listarAtivas() {
        List<Categoria> categorias = categoriaService.listarAtivas();
        return ResponseEntity.ok(categorias);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENDENTE')")
    public ResponseEntity<Categoria> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaDTO dto) {
        Categoria categoria = categoriaService.atualizar(id, dto);
        return ResponseEntity.ok(categoria);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        categoriaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
