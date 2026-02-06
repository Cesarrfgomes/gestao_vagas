package com.rocketseat.gestao_vagas.modules.candidate.controllers;

import com.rocketseat.gestao_vagas.exceptions.UserAlreadyExistsException;
import com.rocketseat.gestao_vagas.modules.candidate.entity.CandidateEntity;
import com.rocketseat.gestao_vagas.modules.candidate.use_cases.CreateCandidateUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @PostMapping
    public CandidateEntity create (@Valid @RequestBody CandidateEntity candidate) throws UserAlreadyExistsException {
        return this.createCandidateUseCase.execute(candidate);
    }
}
