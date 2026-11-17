package com.cidadeemdia.dto;

import jakarta.validation.constraints.*;

public record OcorrenciaDTO(
    Long id,
    
    @NotBlank(message = "Título é obrigatório")
    @Size(min = 5, max = 100)
    String titulo,
    
    @NotBlank(message = "Descrição é obrigatória")
    @Size(min = 10, max = 500)
    String descricao,
    
    @NotBlank(message = "Rua é obrigatória")
    String rua,
    
    @NotBlank(message = "Número é obrigatório")
    String numero,
    
    @NotBlank(message = "Bairro é obrigatório")
    String bairro,
    
    @NotBlank(message = "Cidade é obrigatória")
    String cidade,
    
    String referencia,
    
    String foto,
    
    @NotNull(message = "Urgência é obrigatória")
    String urgencia,
    
    @NotNull(message = "Prioridade é obrigatória")
    String prioridade,
    
    String status,
    
    @NotNull(message = "Categoria é obrigatória")
    Long categoriaId,
    
    @NotNull(message = "Autor é obrigatório")
    Long autorId,
    
    Integer totalConfirmacoes
) { }
