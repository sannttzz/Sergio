package com.cidadeemdia.repository;

import com.cidadeemdia.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
    List<Categoria> findByAtivaTrue();
    
    @Query("SELECT c FROM Categoria c WHERE c.ativa = true AND c.ocorrencias.size > 0")
    List<Categoria> findAttoasComOcorrencias();
}
