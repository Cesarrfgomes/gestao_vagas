package com.rocketseat.gestao_vagas.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.rocketseat.gestao_vagas.modules.candidate.entities.CandidateEntity;
import com.rocketseat.gestao_vagas.modules.company.entities.CompanyEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Service
public class JWTProvider {

    @Value("${security.token.secret.company}")
    private String companySecretKey;

    @Value("${security.token.secret.candidate}")
    private String candidateSecretKey;

    private List<String> roles = Arrays.asList("candidate", "company");

    private Algorithm algorithm;

    public String validateCompanyToken(String token) throws TokenExpiredException {
        token = token.replace("Bearer ", "");

        this.algorithm = Algorithm.HMAC256(companySecretKey);

        return  JWT.require(algorithm).build().verify(token).getSubject();
    }

    public String validateCandidateToken(String token) throws TokenExpiredException {
        token = token.replace("Bearer ", "");

        this.algorithm = Algorithm.HMAC256(candidateSecretKey);

        return  JWT.require(algorithm).build().verify(token).getSubject();
    }

    private String generateToken(String subject, Algorithm algorithm, Instant expiresIn, String role) throws TokenExpiredException {
         return JWT.create()
                .withIssuer("javagas")
                 .withClaim("roles", role)
                .withExpiresAt(expiresIn)
                .withSubject(subject)
                .sign(algorithm);
    }

    public String generateCompanyToken(CompanyEntity company) {
        this.algorithm = Algorithm.HMAC256(companySecretKey);
        Instant twoHours = Instant.now().plus(Duration.ofHours(2));
        return generateToken(company.getId().toString(), algorithm, twoHours, "company");
    }

    public String generateCandidateToken(CandidateEntity candidate, Instant expiresIn) {
        this.algorithm = Algorithm.HMAC256(candidateSecretKey);
        return generateToken(candidate.getId().toString(),  algorithm, expiresIn, "candidate");
    }

}
