package com.cidadeemdia.dto;

import jakarta.validation.constraints.*;

public record CategoriaDTO(
    Long id,
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 50)
    String nome,
    
    String descricao,
    
    Boolean ativa
) { }
