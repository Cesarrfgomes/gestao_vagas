package com.rocketseat.gestao_vagas.modules.company.controllers;

import com.rocketseat.gestao_vagas.modules.candidate.dto.GetCandidateProfileResponseDto;
import com.rocketseat.gestao_vagas.modules.company.dto.CreateJobDto;
import com.rocketseat.gestao_vagas.modules.company.entities.JobEntity;
import com.rocketseat.gestao_vagas.modules.company.use_cases.CreateJobUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/company/job")
public class JobController {

    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping("/")
    @PreAuthorize("hasRole('COMPANY')")
    @Tag(name = "Vagas", description = "Informações das vagas")
    @Operation(summary = "Cadastro de vagas")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = JobEntity.class) )
            })
    })
    public JobEntity create(@Valid @RequestBody CreateJobDto createJobDto, HttpServletRequest request) {
        var companyId = request.getAttribute("company_id");

        var jobEntity = JobEntity.builder()
                .benefits(createJobDto.getBenefits())
                .companyId(UUID.fromString(companyId.toString()))
                .description(createJobDto.getDescription())
                .level(createJobDto.getLevel())
                .build();

        return this.createJobUseCase.execute(jobEntity);
    }
}
