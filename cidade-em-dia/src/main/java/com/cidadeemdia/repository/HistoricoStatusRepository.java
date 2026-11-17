package com.cidadeemdia.repository;

import com.cidadeemdia.entity.HistoricoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricoStatusRepository extends JpaRepository<HistoricoStatus, Long> {
    
    @Query("SELECT h FROM HistoricoStatus h WHERE h.ocorrencia.id = :ocorrenciaId ORDER BY h.data DESC")
    List<HistoricoStatus> findByOcorrencia(@Param("ocorrenciaId") Long ocorrenciaId);
}
