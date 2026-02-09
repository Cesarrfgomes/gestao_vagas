package com.rocketseat.gestao_vagas.modules.candidate.controllers;

import com.rocketseat.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDto;
import com.rocketseat.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDto;
import com.rocketseat.gestao_vagas.modules.candidate.use_cases.AuthCandidateUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/auth")
public class AuthCandidateController {
    @Autowired
    private AuthCandidateUseCase authCandidateUseCase;

    @PostMapping("/candidate")
    public ResponseEntity<AuthCandidateResponseDto> create(@RequestBody AuthCandidateRequestDto authCandidateRequestDto) throws AuthenticationException {
        String token = this.authCandidateUseCase.execute(authCandidateRequestDto);

        return ResponseEntity.ok(new AuthCandidateResponseDto(token));
    }
}
