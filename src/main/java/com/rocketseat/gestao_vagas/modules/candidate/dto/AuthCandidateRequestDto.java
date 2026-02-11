package com.rocketseat.gestao_vagas.modules.candidate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


public record AuthCandidateRequestDto(
         String username,
         String password
) {
}
