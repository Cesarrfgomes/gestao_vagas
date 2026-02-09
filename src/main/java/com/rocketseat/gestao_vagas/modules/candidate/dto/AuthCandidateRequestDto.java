package com.rocketseat.gestao_vagas.modules.candidate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthCandidateRequestDto {
    private String username;
    private String password;
}
