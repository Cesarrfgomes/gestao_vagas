package com.rocketseat.gestao_vagas.modules.candidate.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.UUID;

@Data
public class CandidateEntity {
    private UUID id;
    private String name;

    @Pattern(regexp = "^(?!\\s*$).+", message = "O campo [username] não deve conter espaço")
    private String username;
    @Email(message = "O cmapo [email] deve conter um e-mail válido")
    private String email;
    private String password;
    private String description;
    private String curriculum;
}

