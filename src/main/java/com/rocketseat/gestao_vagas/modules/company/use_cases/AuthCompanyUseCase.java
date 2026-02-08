package com.rocketseat.gestao_vagas.modules.company.use_cases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.rocketseat.gestao_vagas.exceptions.NotFoundException;
import com.rocketseat.gestao_vagas.modules.company.dto.AuthCompanyDto;
import com.rocketseat.gestao_vagas.modules.company.entities.CompanyEntity;
import com.rocketseat.gestao_vagas.modules.company.repositories.CompanyRepository;
import com.rocketseat.gestao_vagas.providers.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;

@Service
public class AuthCompanyUseCase {

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String execute(AuthCompanyDto authCompanyDto) throws AuthenticationException {
        var company = this.companyRepository
                .findByUsername(authCompanyDto.getUsername())
                .orElseThrow(() -> new AuthenticationException("Credenciais incorretas."));

        var doesPasswordMatches = this.passwordEncoder.matches(authCompanyDto.getPassword(), company.getPassword());

        if (!doesPasswordMatches) {
            throw new AuthenticationException("Credenciais incorretas.");
        }

        return jwtProvider.generateToken(company);
    }

}
