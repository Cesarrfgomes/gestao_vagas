package com.rocketseat.gestao_vagas.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
public class ErrorMessageDto {
    private HttpStatus status;
    private String message;
}

