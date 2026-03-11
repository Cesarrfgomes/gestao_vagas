package com.rocketseat.gestao_vagas.modules.candidate.controllers;

import com.rocketseat.gestao_vagas.exceptions.AlreadyExistsException;
import com.rocketseat.gestao_vagas.exceptions.NotFoundException;
import com.rocketseat.gestao_vagas.modules.candidate.dto.GetCandidateProfileResponseDto;
import com.rocketseat.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import com.rocketseat.gestao_vagas.modules.candidate.entities.CandidateEntity;
import com.rocketseat.gestao_vagas.modules.candidate.use_cases.ApplyJobCandidateUseCase;
import com.rocketseat.gestao_vagas.modules.candidate.use_cases.CreateCandidateUseCase;
import com.rocketseat.gestao_vagas.modules.candidate.use_cases.GetCandidateProfileUseCase;
import com.rocketseat.gestao_vagas.modules.candidate.use_cases.ListAllJobsByFilterUseCase;
import com.rocketseat.gestao_vagas.modules.company.entities.JobEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class CandidateControllerImpl implements CandidateController {
    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private GetCandidateProfileUseCase getCandidateProfileUseCase;

    @Autowired
    private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

    @Autowired
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<CandidateEntity> create(CandidateEntity candidate) throws AlreadyExistsException {
        var encodedPassword = passwordEncoder.encode(candidate.getPassword());

        candidate.setPassword(encodedPassword);

        var result = this.createCandidateUseCase.execute(candidate);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Override
    public ResponseEntity<GetCandidateProfileResponseDto> getProfile(HttpServletRequest request) throws NotFoundException {
        var candidateId = request.getAttribute("candidate_id");

        GetCandidateProfileResponseDto candidate = this.getCandidateProfileUseCase.execute(UUID.fromString(candidateId.toString()));

        return ResponseEntity.ok(candidate);
    }

    @Override
    public ResponseEntity<List<JobEntity>> listAllJobsByFilter(String filter) {
        var result = this.listAllJobsByFilterUseCase.execute(filter);

        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<ApplyJobEntity> applyJob(HttpServletRequest request, @RequestBody UUID jobId) throws NotFoundException {
        var candidateId = request.getAttribute("candidate_id");

        var result = this.applyJobCandidateUseCase.execute(UUID.fromString(candidateId.toString()), jobId);

        return ResponseEntity.ok(result);
    }
}
