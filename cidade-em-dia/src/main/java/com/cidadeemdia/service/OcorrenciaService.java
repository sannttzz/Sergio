package com.cidadeemdia.service;

import com.cidadeemdia.dto.OcorrenciaDTO;
import com.cidadeemdia.dto.AlterarStatusDTO;
import com.cidadeemdia.entity.Ocorrencia;
import com.cidadeemdia.entity.Categoria;
import com.cidadeemdia.entity.Usuario;
import com.cidadeemdia.entity.HistoricoStatus;
import com.cidadeemdia.exception.RecursoNaoEncontradoException;
import com.cidadeemdia.exception.ViolacaoRegraNegocionException;
import com.cidadeemdia.repository.OcorrenciaRepository;
import com.cidadeemdia.repository.HistoricoStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OcorrenciaService {
    
    @Autowired
    private OcorrenciaRepository ocorrenciaRepository;
    
    @Autowired
    private HistoricoStatusRepository historicoRepository;
    
    @Autowired
    private CategoriaService categoriaService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    public Ocorrencia criar(OcorrenciaDTO dto) {
        Categoria categoria = categoriaService.obterPorId(dto.categoriaId());
        Usuario autor = usuarioService.obterPorId(dto.autorId());
        
        /**
         * REGRA DE NEGÓCIO 2: Categoria deve estar ativa para permitir novas ocorrências
         */
        if (!categoria.getAtiva()) {
            throw new ViolacaoRegraNegocionException(
                "Não é possível registrar ocorrência na categoria '" + categoria.getNome() + 
                "' pois ela está inativa"
            );
        }
        
        Ocorrencia ocorrencia = new Ocorrencia();
        ocorrencia.setTitulo(dto.titulo());
        ocorrencia.setDescricao(dto.descricao());
        ocorrencia.setRua(dto.rua());
        ocorrencia.setNumero(dto.numero());
        ocorrencia.setBairro(dto.bairro());
        ocorrencia.setCidade(dto.cidade());
        ocorrencia.setReferencia(dto.referencia());
        ocorrencia.setFoto(dto.foto());
        ocorrencia.setUrgencia(Ocorrencia.Urgencia.valueOf(dto.urgencia()));
        ocorrencia.setPrioridade(Ocorrencia.Prioridade.valueOf(dto.prioridade()));
        ocorrencia.setStatus(Ocorrencia.Status.NOVA);
        ocorrencia.setCategoria(categoria);
        ocorrencia.setAutor(autor);
        
        return ocorrenciaRepository.save(ocorrencia);
    }
    
    public Ocorrencia obterPorId(Long id) {
        return ocorrenciaRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Ocorrência com ID " + id + " não encontrada"));
    }
    
    public List<Ocorrencia> listar() {
        return ocorrenciaRepository.findAllOrderByData();
    }
    
    public List<Ocorrencia> listarPorCategoria(Long categoriaId) {
        return ocorrenciaRepository.findByCategoria(categoriaId);
    }
    
    public List<Ocorrencia> listarPorStatus(String status) {
        Ocorrencia.Status statusEnum = Ocorrencia.Status.valueOf(status);
        return ocorrenciaRepository.findByStatus(statusEnum);
    }
    
    public List<Ocorrencia> listarPorAutor(Long autorId) {
        return ocorrenciaRepository.findByAutor(autorId);
    }
    
    public List<Ocorrencia> listarPorUrgencia(String urgencia) {
        Ocorrencia.Urgencia urgenciaEnum = Ocorrencia.Urgencia.valueOf(urgencia);
        return ocorrenciaRepository.findByUrgencia(urgenciaEnum);
    }
    
    public Ocorrencia atualizar(Long id, OcorrenciaDTO dto) {
        Ocorrencia ocorrencia = obterPorId(id);
        ocorrencia.setTitulo(dto.titulo());
        ocorrencia.setDescricao(dto.descricao());
        ocorrencia.setRua(dto.rua());
        ocorrencia.setNumero(dto.numero());
        ocorrencia.setBairro(dto.bairro());
        ocorrencia.setCidade(dto.cidade());
        ocorrencia.setReferencia(dto.referencia());
        ocorrencia.setFoto(dto.foto());
        ocorrencia.setUrgencia(Ocorrencia.Urgencia.valueOf(dto.urgencia()));
        ocorrencia.setPrioridade(Ocorrencia.Prioridade.valueOf(dto.prioridade()));
        
        return ocorrenciaRepository.save(ocorrencia);
    }
    
    /**
     * Funcionalidade específica: Alterar status de ocorrência com registro em histórico
     */
    public Ocorrencia alterarStatus(Long id, AlterarStatusDTO dto, Usuario usuarioAtual) {
        Ocorrencia ocorrencia = obterPorId(id);
        Ocorrencia.Status statusNovo = Ocorrencia.Status.valueOf(dto.statusNovo());
        Ocorrencia.Status statusAnterior = ocorrencia.getStatus();
        
        ocorrencia.setStatus(statusNovo);
        Ocorrencia ocorrenciaAtualizada = ocorrenciaRepository.save(ocorrencia);
        
        // Registrar no histórico
        HistoricoStatus historico = new HistoricoStatus();
        historico.setOcorrencia(ocorrenciaAtualizada);
        historico.setUsuario(usuarioAtual);
        historico.setStatusAnterior(statusAnterior);
        historico.setStatusNovo(statusNovo);
        historico.setMotivo(dto.motivo());
        historico.setPerfilQueAlterou(
            HistoricoStatus.Perfil.valueOf(usuarioAtual.getPerfil().toString())
        );
        
        historicoRepository.save(historico);
        
        return ocorrenciaAtualizada;
    }
    
    public void excluir(Long id) {
        Ocorrencia ocorrencia = obterPorId(id);
        ocorrenciaRepository.delete(ocorrencia);
    }
}
