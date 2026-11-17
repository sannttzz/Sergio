package com.cidadeemdia.repository;

import com.cidadeemdia.entity.Confirmacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfirmacaoRepository extends JpaRepository<Confirmacao, Long> {
    
    @Query("SELECT c FROM Confirmacao c WHERE c.ocorrencia.id = :ocorrenciaId ORDER BY c.dataCriacao DESC")
    List<Confirmacao> findByOcorrencia(@Param("ocorrenciaId") Long ocorrenciaId);
    
    @Query("SELECT c FROM Confirmacao c WHERE c.usuario.id = :usuarioId ORDER BY c.dataCriacao DESC")
    List<Confirmacao> findByUsuario(@Param("usuarioId") Long usuarioId);
}
