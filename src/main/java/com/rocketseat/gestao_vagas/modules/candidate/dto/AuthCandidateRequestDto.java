package com.rocketseat.gestao_vagas.modules.candidate.dto;


import io.swagger.v3.oas.annotations.media.Schema;

public record AuthCandidateRequestDto(
        @Schema(example = "seu_usuario")
         String username,
         @Schema(example = "sua_senha")
         String password
) {
}
