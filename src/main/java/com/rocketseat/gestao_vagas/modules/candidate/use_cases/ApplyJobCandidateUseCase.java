package com.rocketseat.gestao_vagas.modules.candidate.use_cases;

import com.rocketseat.gestao_vagas.exceptions.NotFoundException;
import com.rocketseat.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import com.rocketseat.gestao_vagas.modules.candidate.repositories.ApplyJobRepository;
import com.rocketseat.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import com.rocketseat.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplyJobCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplyJobRepository applyJobRepository;

    public ApplyJobEntity execute(UUID candidateId, UUID jobId) {
        this.candidateRepository
                .findById(candidateId)
                .orElseThrow(() -> new NotFoundException("Candidato não encontrado."));


        this.jobRepository.findById(jobId)
                .orElseThrow(() -> new NotFoundException("Vaga não encontrada."));


        var applyJob = ApplyJobEntity.builder().candidateId(candidateId).jobId(jobId).build();

        return applyJobRepository.save(applyJob);
    }
}
