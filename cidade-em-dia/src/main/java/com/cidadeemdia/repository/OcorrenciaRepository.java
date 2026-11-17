package com.cidadeemdia.repository;

import com.cidadeemdia.entity.Ocorrencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {
    
    @Query("SELECT o FROM Ocorrencia o WHERE o.categoria.id = :categoriaId")
    List<Ocorrencia> findByCategoria(@Param("categoriaId") Long categoriaId);
    
    @Query("SELECT o FROM Ocorrencia o WHERE o.status = :status ORDER BY o.dataCriacao DESC")
    List<Ocorrencia> findByStatus(@Param("status") Ocorrencia.Status status);
    
    @Query("SELECT o FROM Ocorrencia o WHERE o.autor.id = :autorId ORDER BY o.dataCriacao DESC")
    List<Ocorrencia> findByAutor(@Param("autorId") Long autorId);
    
    @Query("SELECT o FROM Ocorrencia o WHERE o.urgencia = :urgencia ORDER BY o.dataCriacao DESC")
    List<Ocorrencia> findByUrgencia(@Param("urgencia") Ocorrencia.Urgencia urgencia);
    
    @Query("SELECT o FROM Ocorrencia o ORDER BY o.dataCriacao DESC")
    List<Ocorrencia> findAllOrderByData();
}
