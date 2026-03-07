package com.rocketseat.gestao_vagas.modules.company.controllers;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.rocketseat.gestao_vagas.exceptions.AlreadyExistsException;
import com.rocketseat.gestao_vagas.modules.company.dto.CreateJobDto;
import com.rocketseat.gestao_vagas.modules.company.entities.CompanyEntity;
import com.rocketseat.gestao_vagas.modules.company.entities.JobEntity;
import com.rocketseat.gestao_vagas.modules.company.use_cases.CreateCompanyUseCase;
import com.rocketseat.gestao_vagas.modules.company.use_cases.CreateJobUseCase;
import com.rocketseat.gestao_vagas.modules.company.use_cases.FindCompanyById;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/company")
@Tag(name = "Empresa")
public class CompanyController {
    @Autowired
    private CreateCompanyUseCase createCompanyUseCase;

    @Autowired
    private CreateJobUseCase createJobUseCase;


    @PostMapping
    @Operation(summary = "Cadastro de empresas")
    public ResponseEntity<CompanyEntity> create(@Valid @RequestBody CompanyEntity companyEntity) throws AlreadyExistsException {
        var result = this.createCompanyUseCase.execute(companyEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("/job")
    @PreAuthorize("hasRole('COMPANY')")
    @Operation(summary = "Cadastro de vagas")
    public ResponseEntity<JobEntity> createJob(@Valid @RequestBody CreateJobDto jobDto, HttpServletRequest request) throws TokenExpiredException {
        var companyId = request.getAttribute("company_id");

        var jobEntity = JobEntity.builder()
                .description(jobDto.getDescription())
                .benefits(jobDto.getBenefits())
                .level(jobDto.getLevel())
                .companyId(UUID.fromString(companyId.toString()))
                .build();

        var result = this.createJobUseCase.execute(jobEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
