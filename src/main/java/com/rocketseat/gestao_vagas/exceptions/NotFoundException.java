package com.rocketseat.gestao_vagas.exceptions;

public class NotFoundException extends  RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
