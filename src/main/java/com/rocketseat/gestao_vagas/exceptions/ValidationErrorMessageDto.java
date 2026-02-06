package com.rocketseat.gestao_vagas.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationErrorMessageDto {
    private String message;
    private String field;
}
