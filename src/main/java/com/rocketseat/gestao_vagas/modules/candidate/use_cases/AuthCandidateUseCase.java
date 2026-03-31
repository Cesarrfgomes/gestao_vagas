package com.rocketseat.gestao_vagas.modules.candidate.use_cases;

import com.rocketseat.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import com.rocketseat.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDTO;
import com.rocketseat.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import com.rocketseat.gestao_vagas.providers.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;

@Service
public class AuthCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTProvider jwtProvider;


    public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDto) throws AuthenticationException {
        var candidate = this.candidateRepository.findByUsername(authCandidateRequestDto.username())
                .orElseThrow(() -> new AuthenticationException("Credenciais incorretas."));

        var doesPasswordMatches = this.passwordEncoder.matches(authCandidateRequestDto.password(), candidate.getPassword());

        if (!doesPasswordMatches) {
            throw new AuthenticationException("Credenciais incorretas.");
        }

        Duration twoHours = Duration.ofHours(2);

        Instant expiresIn = Instant.now().plus(twoHours);


        var token = this.jwtProvider.generateCandidateToken(candidate, expiresIn);

        var authCandidateResponse = AuthCandidateResponseDTO
                .builder()
                .accessToken(token)
                .expiresIn(twoHours.toSeconds())
                .build();

        return authCandidateResponse;
    }
}
