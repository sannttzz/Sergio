package com.cidadeemdia.controller;

import com.cidadeemdia.dto.OcorrenciaDTO;
import com.cidadeemdia.dto.AlterarStatusDTO;
import com.cidadeemdia.entity.Ocorrencia;
import com.cidadeemdia.entity.Usuario;
import com.cidadeemdia.service.OcorrenciaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ocorrencias")
@CrossOrigin(origins = "*", maxAge = 3600)
public class OcorrenciaController {
    
    @Autowired
    private OcorrenciaService ocorrenciaService;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENDENTE', 'CIDADAO')")
    public ResponseEntity<Ocorrencia> criar(@Valid @RequestBody OcorrenciaDTO dto) {
        Ocorrencia ocorrencia = ocorrenciaService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ocorrencia);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENDENTE', 'CIDADAO')")
    public ResponseEntity<Ocorrencia> obterPorId(@PathVariable Long id) {
        Ocorrencia ocorrencia = ocorrenciaService.obterPorId(id);
        return ResponseEntity.ok(ocorrencia);
    }
    
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENDENTE', 'CIDADAO')")
    public ResponseEntity<List<Ocorrencia>> listar() {
        List<Ocorrencia> ocorrencias = ocorrenciaService.listar();
        return ResponseEntity.ok(ocorrencias);
    }
    
    @GetMapping("/categoria/{categoriaId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENDENTE', 'CIDADAO')")
    public ResponseEntity<List<Ocorrencia>> listarPorCategoria(@PathVariable Long categoriaId) {
        List<Ocorrencia> ocorrencias = ocorrenciaService.listarPorCategoria(categoriaId);
        return ResponseEntity.ok(ocorrencias);
    }
    
    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENDENTE', 'CIDADAO')")
    public ResponseEntity<List<Ocorrencia>> listarPorStatus(@PathVariable String status) {
        List<Ocorrencia> ocorrencias = ocorrenciaService.listarPorStatus(status);
        return ResponseEntity.ok(ocorrencias);
    }
    
    @GetMapping("/autor/{autorId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENDENTE', 'CIDADAO')")
    public ResponseEntity<List<Ocorrencia>> listarPorAutor(@PathVariable Long autorId) {
        List<Ocorrencia> ocorrencias = ocorrenciaService.listarPorAutor(autorId);
        return ResponseEntity.ok(ocorrencias);
    }
    
    @GetMapping("/urgencia/{urgencia}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENDENTE', 'CIDADAO')")
    public ResponseEntity<List<Ocorrencia>> listarPorUrgencia(@PathVariable String urgencia) {
        List<Ocorrencia> ocorrencias = ocorrenciaService.listarPorUrgencia(urgencia);
        return ResponseEntity.ok(ocorrencias);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENDENTE')")
    public ResponseEntity<Ocorrencia> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody OcorrenciaDTO dto) {
        Ocorrencia ocorrencia = ocorrenciaService.atualizar(id, dto);
        return ResponseEntity.ok(ocorrencia);
    }
    
    /**
     * Endpoint específico para alterar status com registro em histórico
     */
    @PostMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENDENTE')")
    public ResponseEntity<Ocorrencia> alterarStatus(
            @PathVariable Long id,
            @RequestBody AlterarStatusDTO dto,
            Authentication authentication) {
        
        // Obter usuário autenticado (seria necessário implementar UserDetails customizado)
        // Para esta implementação, usaremos um workaround
        Ocorrencia ocorrencia = ocorrenciaService.obterPorId(id);
        Usuario usuarioAtual = ocorrencia.getAutor(); // Simplificado para demo
        
        Ocorrencia ocorrenciaAtualizada = ocorrenciaService.alterarStatus(id, dto, usuarioAtual);
        return ResponseEntity.ok(ocorrenciaAtualizada);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        ocorrenciaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
