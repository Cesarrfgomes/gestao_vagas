package com.rocketseat.gestao_vagas.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.rocketseat.gestao_vagas.modules.candidate.entities.CandidateEntity;
import com.rocketseat.gestao_vagas.modules.company.entities.CompanyEntity;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class JWTProvider {

    @Value("${security.token.secret.company}")
    private String companySecretKey;

    @Value("${security.token.secret.candidate}")
    private String candidateSecretKey;

    private Algorithm algorithm;

    public String validateToken(String token) throws TokenExpiredException {
        token = token.replace("Bearer ", "");

        return  JWT.require(algorithm).build().verify(token).getSubject();
    }

    private String generateToken(String subject, Algorithm algorithm) throws TokenExpiredException {
         return JWT.create()
                .withIssuer("javagas")
                .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
                .withSubject(subject)
                .sign(algorithm);
    }

    public String generateCompanyToken(CompanyEntity company) {
        this.algorithm = Algorithm.HMAC256(companySecretKey);
        return generateToken(company.getId().toString(), algorithm);
    }

    public String generateCandidateToken(CandidateEntity candidate) {
        this.algorithm = Algorithm.HMAC256(candidateSecretKey);
        return generateToken(candidate.getId().toString(),  algorithm);
    }

}
