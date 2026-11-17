package com.cidadeemdia.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@Getter
public class ErroResponse {
    
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String mensagem;
    private String caminho;
    
    public ErroResponse(String mensagem, HttpStatus status, String caminho) {
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.mensagem = mensagem;
        this.caminho = caminho;
    }
    
    public ErroResponse(String mensagem, HttpStatus status) {
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.mensagem = mensagem;
    }
}
