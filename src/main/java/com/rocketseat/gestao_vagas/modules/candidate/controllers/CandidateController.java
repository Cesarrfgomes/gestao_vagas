package com.rocketseat.gestao_vagas.modules.candidate.controllers;

import com.rocketseat.gestao_vagas.exceptions.AlreadyExistsException;
import com.rocketseat.gestao_vagas.exceptions.NotFoundException;
import com.rocketseat.gestao_vagas.modules.candidate.dto.GetCandidateProfileResponseDto;
import com.rocketseat.gestao_vagas.modules.candidate.entities.CandidateEntity;
import com.rocketseat.gestao_vagas.modules.candidate.use_cases.CreateCandidateUseCase;
import com.rocketseat.gestao_vagas.modules.candidate.use_cases.GetCandidateProfileUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private GetCandidateProfileUseCase getCandidateProfileUseCase;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<CandidateEntity> create(@Valid @RequestBody CandidateEntity candidate) throws AlreadyExistsException {
        var encodedPassword = passwordEncoder.encode(candidate.getPassword());

        candidate.setPassword(encodedPassword);

        var result = this.createCandidateUseCase.execute(candidate);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/profile")
    public ResponseEntity<GetCandidateProfileResponseDto> getProfile(HttpServletRequest request) throws NotFoundException {
        var candidateId = request.getAttribute("candidate_id");

        GetCandidateProfileResponseDto candidate = this.getCandidateProfileUseCase.execute(UUID.fromString(candidateId.toString()));

        return ResponseEntity.ok(candidate);
    }
}
