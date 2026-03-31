package com.rocketseat.gestao_vagas.modules.candidate.controllers;

import com.rocketseat.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import com.rocketseat.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.naming.AuthenticationException;

@RequestMapping("/auth")
@Tag(name = "Candidato")
public interface AuthCandidateController {
    @PostMapping("/candidate")
    @Operation(summary = "Autenticação do candidadto")
    ResponseEntity<AuthCandidateResponseDTO> auth(@RequestBody AuthCandidateRequestDTO authCandidateRequestDto) throws AuthenticationException ;
}
