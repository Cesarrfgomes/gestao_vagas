package com.rocketseat.gestao_vagas.exceptions;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(){
        super("Usuário já existe");
    }
}
