package com.cidadeemdia.controller;

import com.cidadeemdia.dto.ConfirmacaoDTO;
import com.cidadeemdia.entity.Confirmacao;
import com.cidadeemdia.service.ConfirmacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/confirmacoes")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ConfirmacaoController {
    
    @Autowired
    private ConfirmacaoService confirmacaoService;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENDENTE', 'CIDADAO')")
    public ResponseEntity<Confirmacao> criar(@Valid @RequestBody ConfirmacaoDTO dto) {
        Confirmacao confirmacao = confirmacaoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(confirmacao);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENDENTE', 'CIDADAO')")
    public ResponseEntity<Confirmacao> obterPorId(@PathVariable Long id) {
        Confirmacao confirmacao = confirmacaoService.obterPorId(id);
        return ResponseEntity.ok(confirmacao);
    }
    
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENDENTE', 'CIDADAO')")
    public ResponseEntity<List<Confirmacao>> listar() {
        List<Confirmacao> confirmacoes = confirmacaoService.listar();
        return ResponseEntity.ok(confirmacoes);
    }
    
    @GetMapping("/ocorrencia/{ocorrenciaId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENDENTE', 'CIDADAO')")
    public ResponseEntity<List<Confirmacao>> listarPorOcorrencia(@PathVariable Long ocorrenciaId) {
        List<Confirmacao> confirmacoes = confirmacaoService.listarPorOcorrencia(ocorrenciaId);
        return ResponseEntity.ok(confirmacoes);
    }
    
    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENDENTE', 'CIDADAO')")
    public ResponseEntity<List<Confirmacao>> listarPorUsuario(@PathVariable Long usuarioId) {
        List<Confirmacao> confirmacoes = confirmacaoService.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(confirmacoes);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        confirmacaoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
