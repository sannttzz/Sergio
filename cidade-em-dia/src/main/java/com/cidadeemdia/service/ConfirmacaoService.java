package com.cidadeemdia.service;

import com.cidadeemdia.dto.ConfirmacaoDTO;
import com.cidadeemdia.entity.Confirmacao;
import com.cidadeemdia.entity.Ocorrencia;
import com.cidadeemdia.entity.Usuario;
import com.cidadeemdia.exception.RecursoNaoEncontradoException;
import com.cidadeemdia.repository.ConfirmacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ConfirmacaoService {
    
    @Autowired
    private ConfirmacaoRepository confirmacaoRepository;
    
    @Autowired
    private OcorrenciaService ocorrenciaService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    public Confirmacao criar(ConfirmacaoDTO dto) {
        Ocorrencia ocorrencia = ocorrenciaService.obterPorId(dto.ocorrenciaId());
        Usuario usuario = usuarioService.obterPorId(dto.usuarioId());
        
        Confirmacao confirmacao = new Confirmacao();
        confirmacao.setOcorrencia(ocorrencia);
        confirmacao.setUsuario(usuario);
        confirmacao.setComentario(dto.comentario());
        
        return confirmacaoRepository.save(confirmacao);
    }
    
    public Confirmacao obterPorId(Long id) {
        return confirmacaoRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Confirmação com ID " + id + " não encontrada"));
    }
    
    public List<Confirmacao> listar() {
        return confirmacaoRepository.findAll();
    }
    
    public List<Confirmacao> listarPorOcorrencia(Long ocorrenciaId) {
        return confirmacaoRepository.findByOcorrencia(ocorrenciaId);
    }
    
    public List<Confirmacao> listarPorUsuario(Long usuarioId) {
        return confirmacaoRepository.findByUsuario(usuarioId);
    }
    
    public void excluir(Long id) {
        Confirmacao confirmacao = obterPorId(id);
        confirmacaoRepository.delete(confirmacao);
    }
}
