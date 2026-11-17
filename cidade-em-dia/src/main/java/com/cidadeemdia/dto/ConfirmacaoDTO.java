package com.cidadeemdia.dto;

import jakarta.validation.constraints.*;

public record ConfirmacaoDTO(
    Long id,
    
    @NotNull(message = "Ocorrência é obrigatória")
    Long ocorrenciaId,
    
    @NotNull(message = "Usuário é obrigatório")
    Long usuarioId,
    
    String comentario
) { }
