package com.rocketseat.gestao_vagas.modules.company.use_cases;

import com.rocketseat.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import com.rocketseat.gestao_vagas.modules.company.repositories.CompanyRepository;
import com.rocketseat.gestao_vagas.providers.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
public class AuthCompanyUseCase {

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String execute(AuthCompanyDTO authCompanyDto) throws AuthenticationException {
        var company = this.companyRepository
                .findByUsername(authCompanyDto.getUsername())
                .orElseThrow(() -> new AuthenticationException("Credenciais incorretas."));

        var doesPasswordMatches = this.passwordEncoder.matches(authCompanyDto.getPassword(), company.getPassword());

        if (!doesPasswordMatches) {
            throw new AuthenticationException("Credenciais incorretas.");
        }

        return jwtProvider.generateCompanyToken(company);
    }

}
