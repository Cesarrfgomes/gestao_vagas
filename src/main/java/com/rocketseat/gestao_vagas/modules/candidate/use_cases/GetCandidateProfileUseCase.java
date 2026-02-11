package com.rocketseat.gestao_vagas.modules.candidate.use_cases;

import com.rocketseat.gestao_vagas.exceptions.NotFoundException;
import com.rocketseat.gestao_vagas.modules.candidate.dto.GetCandidateProfileResponseDto;
import com.rocketseat.gestao_vagas.modules.candidate.entities.CandidateEntity;
import com.rocketseat.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetCandidateProfileUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public GetCandidateProfileResponseDto execute(UUID candidateId) throws NotFoundException {
        CandidateEntity candidate = this.candidateRepository
                .findById(candidateId)
                .orElseThrow(() -> new NotFoundException("Candidato não encontrado."));

        var getCandidateProfileResponse = GetCandidateProfileResponseDto.builder()
                .id(candidateId)
                .name(candidate.getName())
                .username(candidate.getUsername())
                .description(candidate.getDescription())
                .email(candidate.getEmail())
                .curriculum(candidate.getCurriculum())
                .build();

        return  getCandidateProfileResponse;
    }
}
