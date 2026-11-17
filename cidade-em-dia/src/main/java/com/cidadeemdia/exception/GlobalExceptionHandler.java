package com.cidadeemdia.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResponse> tratarRecursoNaoEncontrado(
            RecursoNaoEncontradoException ex,
            WebRequest request) {
        
        ErroResponse erro = new ErroResponse(
            ex.getMessage(),
            HttpStatus.NOT_FOUND
        );
        
        return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(ViolacaoRegraNegocionException.class)
    public ResponseEntity<ErroResponse> tratarViolacaoRegraNegocio(
            ViolacaoRegraNegocionException ex,
            WebRequest request) {
        
        ErroResponse erro = new ErroResponse(
            ex.getMessage(),
            HttpStatus.CONFLICT
        );
        
        return new ResponseEntity<>(erro, HttpStatus.CONFLICT);
    }
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            org.springframework.http.HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        
        Map<String, String> erros = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            erros.put(fieldName, errorMessage);
        });
        
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", java.time.LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Validação falhou");
        body.put("erros", erros);
        
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponse> tratarExcecaoGeral(
            Exception ex,
            WebRequest request) {
        
        ErroResponse erro = new ErroResponse(
            "Erro interno do servidor: " + ex.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
        
        return new ResponseEntity<>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
