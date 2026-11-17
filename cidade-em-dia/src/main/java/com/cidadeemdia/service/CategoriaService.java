package com.cidadeemdia.service;

import com.cidadeemdia.dto.CategoriaDTO;
import com.cidadeemdia.entity.Categoria;
import com.cidadeemdia.exception.RecursoNaoEncontradoException;
import com.cidadeemdia.exception.ViolacaoRegraNegocionException;
import com.cidadeemdia.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoriaService {
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    public Categoria criar(CategoriaDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNome(dto.nome());
        categoria.setDescricao(dto.descricao());
        categoria.setAtiva(dto.ativa() != null ? dto.ativa() : true);
        
        return categoriaRepository.save(categoria);
    }
    
    public Categoria obterPorId(Long id) {
        return categoriaRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria com ID " + id + " não encontrada"));
    }
    
    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }
    
    public List<Categoria> listarAtivas() {
        return categoriaRepository.findByAtivaTrue();
    }
    
    public Categoria atualizar(Long id, CategoriaDTO dto) {
        Categoria categoria = obterPorId(id);
        categoria.setNome(dto.nome());
        categoria.setDescricao(dto.descricao());
        categoria.setAtiva(dto.ativa() != null ? dto.ativa() : categoria.getAtiva());
        
        return categoriaRepository.save(categoria);
    }
    
    /**
     * REGRA DE NEGÓCIO 1: Não permitir exclusão de categoria que possui ocorrências associadas
     */
    public void excluir(Long id) {
        Categoria categoria = obterPorId(id);
        
        if (categoria.getOcorrencias() != null && !categoria.getOcorrencias().isEmpty()) {
            throw new ViolacaoRegraNegocionException(
                "Não é possível excluir a categoria '" + categoria.getNome() + 
                "' pois ela possui " + categoria.getOcorrencias().size() + " ocorrência(s) associada(s)"
            );
        }
        
        categoriaRepository.delete(categoria);
    }
}
