package com.cidadeemdia.repository;

import com.cidadeemdia.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByEmail(String email);
    
    @Query("SELECT u FROM Usuario u WHERE u.perfil = :perfil AND u.ativo = true")
    List<Usuario> findByPerfilAtivos(@Param("perfil") Usuario.Perfil perfil);
    
    List<Usuario> findByAtivoTrue();
}
