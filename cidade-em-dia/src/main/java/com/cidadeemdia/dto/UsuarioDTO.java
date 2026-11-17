package com.cidadeemdia.dto;

import jakarta.validation.constraints.*;

public record UsuarioDTO(
    Long id,
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100)
    String nome,
    
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    String email,
    
    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6)
    String senha,
    
    @NotNull(message = "Perfil é obrigatório")
    String perfil,
    
    Boolean ativo
) { }
