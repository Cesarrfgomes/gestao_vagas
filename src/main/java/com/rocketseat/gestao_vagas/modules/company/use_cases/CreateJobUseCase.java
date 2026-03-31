package com.rocketseat.gestao_vagas.modules.company.use_cases;

import com.rocketseat.gestao_vagas.exceptions.NotFoundException;
import com.rocketseat.gestao_vagas.modules.company.entities.JobEntity;
import com.rocketseat.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateJobUseCase {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private FindCompanyById findCompanyById;

    public JobEntity execute(JobEntity jobEntity) {

        var company = findCompanyById.execute(jobEntity.getCompanyId());

        if (company == null) {
            throw new NotFoundException("Company not found");
        }

        return jobRepository.save(jobEntity);
    }
}
