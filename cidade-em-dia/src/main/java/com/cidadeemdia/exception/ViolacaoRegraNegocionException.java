package com.cidadeemdia.exception;

public class ViolacaoRegraNegocionException extends RuntimeException {
    
    public ViolacaoRegraNegocionException(String mensagem) {
        super(mensagem);
    }
    
    public ViolacaoRegraNegocionException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
