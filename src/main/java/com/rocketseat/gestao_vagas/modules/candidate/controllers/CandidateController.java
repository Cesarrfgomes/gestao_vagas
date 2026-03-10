package com.rocketseat.gestao_vagas.modules.candidate.controllers;

import com.rocketseat.gestao_vagas.exceptions.AlreadyExistsException;
import com.rocketseat.gestao_vagas.exceptions.ErrorMessageDto;
import com.rocketseat.gestao_vagas.exceptions.NotFoundException;
import com.rocketseat.gestao_vagas.modules.candidate.dto.GetCandidateProfileResponseDto;
import com.rocketseat.gestao_vagas.modules.candidate.entities.CandidateEntity;
import com.rocketseat.gestao_vagas.modules.candidate.use_cases.CreateCandidateUseCase;
import com.rocketseat.gestao_vagas.modules.candidate.use_cases.GetCandidateProfileUseCase;
import com.rocketseat.gestao_vagas.modules.candidate.use_cases.ListAllJobsByFilterUseCase;
import com.rocketseat.gestao_vagas.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato")
public class CandidateController {

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private GetCandidateProfileUseCase getCandidateProfileUseCase;

    @Autowired
    private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @Operation(summary = "Cadastro de usuário")
    public ResponseEntity<CandidateEntity> create(@Valid @RequestBody CandidateEntity candidate) throws AlreadyExistsException {
        var encodedPassword = passwordEncoder.encode(candidate.getPassword());

        candidate.setPassword(encodedPassword);

        var result = this.createCandidateUseCase.execute(candidate);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Detalha o perfil do usuário", description = "Detalha o perfil do usuário logado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = GetCandidateProfileResponseDto.class))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageDto.class))
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<GetCandidateProfileResponseDto> getProfile(HttpServletRequest request) throws NotFoundException {
        var candidateId = request.getAttribute("candidate_id");

        GetCandidateProfileResponseDto candidate = this.getCandidateProfileUseCase.execute(UUID.fromString(candidateId.toString()));

        return ResponseEntity.ok(candidate);
    }

    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Listagem de vagas disponiveis para o candidato", description = "Listagem de vagas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<List<JobEntity>> listAllJobsByFilter(@RequestParam(value = "q") String filter) {
        var result = this.listAllJobsByFilterUseCase.execute(filter);

        return ResponseEntity.ok(result);
    }
}
