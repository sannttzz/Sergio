package com.cidadeemdia.service;

import com.cidadeemdia.dto.UsuarioDTO;
import com.cidadeemdia.entity.Usuario;
import com.cidadeemdia.exception.RecursoNaoEncontradoException;
import com.cidadeemdia.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public Usuario criar(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));
        usuario.setPerfil(Usuario.Perfil.valueOf(dto.perfil()));
        usuario.setAtivo(dto.ativo() != null ? dto.ativo() : true);
        
        return usuarioRepository.save(usuario);
    }
    
    public Usuario obterPorId(Long id) {
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário com ID " + id + " não encontrado"));
    }
    
    public Usuario obterPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário com email " + email + " não encontrado"));
    }
    
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }
    
    public List<Usuario> listarAtivos() {
        return usuarioRepository.findByAtivoTrue();
    }
    
    public List<Usuario> listarPorPerfil(String perfil) {
        Usuario.Perfil perfilEnum = Usuario.Perfil.valueOf(perfil);
        return usuarioRepository.findByPerfilAtivos(perfilEnum);
    }
    
    public Usuario atualizar(Long id, UsuarioDTO dto) {
        Usuario usuario = obterPorId(id);
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        if (dto.senha() != null && !dto.senha().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(dto.senha()));
        }
        usuario.setPerfil(Usuario.Perfil.valueOf(dto.perfil()));
        usuario.setAtivo(dto.ativo() != null ? dto.ativo() : usuario.getAtivo());
        
        return usuarioRepository.save(usuario);
    }
    
    public void excluir(Long id) {
        Usuario usuario = obterPorId(id);
        usuarioRepository.delete(usuario);
    }
}
