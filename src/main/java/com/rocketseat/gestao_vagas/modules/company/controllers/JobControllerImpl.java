package com.rocketseat.gestao_vagas.modules.company.controllers;

import com.rocketseat.gestao_vagas.modules.company.dto.CreateJobDTO;
import com.rocketseat.gestao_vagas.modules.company.entities.JobEntity;
import com.rocketseat.gestao_vagas.modules.company.use_cases.CreateJobUseCase;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class JobControllerImpl implements JobController {

    @Autowired
    private CreateJobUseCase createJobUseCase;

    @Override
    public JobEntity create(CreateJobDTO createJobDto, HttpServletRequest request) {
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
