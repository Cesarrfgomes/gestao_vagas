package com.rocketseat.gestao_vagas.modules.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rocketseat.gestao_vagas.modules.candidate.entities.CandidateEntity;
import com.rocketseat.gestao_vagas.modules.company.entities.CompanyEntity;
import org.springframework.beans.factory.annotation.Value;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TestUtils {


    private static final String companySecretKey = "minhasenhasecreta";

    private static final String candidateSecretKey = "minhasenhasegura";

    private static Algorithm algorithm;

    public static String objectToJson(Object object) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public DecodedJWT validateCompanyToken(String token) throws TokenExpiredException {
        token = token.replace("Bearer ", "");

        algorithm = Algorithm.HMAC256(companySecretKey);
        System.out.println(algorithm);

        return JWT.require(algorithm).build().verify(token);
    }

    public DecodedJWT validateCandidateToken(String token) throws TokenExpiredException {
        token = token.replace("Bearer ", "");

        algorithm = Algorithm.HMAC256(candidateSecretKey);

        return JWT.require(algorithm).build().verify(token);
    }

    private static String generateToken(String subject, Algorithm algorithm, Instant expiresIn, List role) throws TokenExpiredException {
        return JWT.create()
                .withIssuer("javagas")
                .withClaim("roles", role)
                .withExpiresAt(expiresIn)
                .withSubject(subject)
                .sign(algorithm);
    }

    public static String generateCompanyToken(CompanyEntity company) {
        algorithm = Algorithm.HMAC256(companySecretKey);
        Instant twoHours = Instant.now().plus(Duration.ofHours(2));
        return generateToken(company.getId().toString(), algorithm, twoHours, Arrays.asList("COMPANY"));
    }

    public String generateCandidateToken(CandidateEntity candidate, Instant expiresIn) {
        algorithm = Algorithm.HMAC256(candidateSecretKey);
        return generateToken(candidate.getId().toString(), algorithm, expiresIn, Arrays.asList("CANDIDATE"));
    }

}
