package com.rocketseat.gestao_vagas.modules.candidate.use_cases;

import com.rocketseat.gestao_vagas.exceptions.UserAlreadyExistsException;
import com.rocketseat.gestao_vagas.modules.candidate.entity.CandidateEntity;
import com.rocketseat.gestao_vagas.modules.candidate.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public CandidateEntity execute(CandidateEntity candidate) {
        this.candidateRepository.findByUsernameOrEmail(candidate.getUsername(), candidate.getEmail())
                .ifPresent((user) -> {
                    throw new UserAlreadyExistsException();
                });

        return this.candidateRepository.save(candidate);
    }
}
