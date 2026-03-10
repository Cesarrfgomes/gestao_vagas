package com.rocketseat.gestao_vagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateJobDto {

    @Schema(example = "Vaga para pessoa desenvolvedora júnior")
    private String description;
    @Schema(example = "GYMPass, Plano de saúde")
    private String benefits;
    @Schema(example = "JUNIOR")
    private String level;
}
