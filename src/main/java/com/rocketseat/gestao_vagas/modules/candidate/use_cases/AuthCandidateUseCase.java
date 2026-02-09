package com.rocketseat.gestao_vagas.modules.candidate.use_cases;

import com.rocketseat.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDto;
import com.rocketseat.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import com.rocketseat.gestao_vagas.providers.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
public class AuthCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTProvider jwtProvider;


    public String execute(AuthCandidateRequestDto authCandidateRequestDto) throws AuthenticationException {
        var candidate = this.candidateRepository.findByUsername(authCandidateRequestDto.getUsername())
                .orElseThrow(() -> new AuthenticationException("Credenciais incorretas."));

        var doesPasswordMatches = this.passwordEncoder.matches(authCandidateRequestDto.getPassword(), candidate.getPassword());

        if(!doesPasswordMatches){
            throw new AuthenticationException("Credenciais incorretas.");
        }

        return jwtProvider.generateCandidateToken(candidate);
    }
}
