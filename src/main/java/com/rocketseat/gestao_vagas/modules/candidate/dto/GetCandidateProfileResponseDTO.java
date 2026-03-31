package com.rocketseat.gestao_vagas.modules.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetCandidateProfileResponseDTO {
    private UUID id;

    @Schema(example = "Cesar")
    private String name;

    @Schema(example = "cesar")
    private String username;

    @Schema(example = "cesar@email.com")
    private String email;

    @Schema(example = "Desenvolvor Java")
    private String description;
    private String curriculum;
}
