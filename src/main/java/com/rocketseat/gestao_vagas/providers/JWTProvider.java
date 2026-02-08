package com.rocketseat.gestao_vagas.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.rocketseat.gestao_vagas.modules.company.entities.CompanyEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class JWTProvider {

    @Value("${security.token.secret}")
    private String secretKey;

    private Algorithm algorithm = Algorithm.HMAC256(secretKey);

    public String validateToken(String token) {
        token = token.replace("Bearer ", "");

        return  JWT.require(algorithm).build().verify(token).getSubject();
    }

    public String generateToken(CompanyEntity company) {
         return JWT.create()
                .withIssuer("javagas")
                .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
                .withSubject(company.getId().toString())
                .sign(algorithm);
    }
}
