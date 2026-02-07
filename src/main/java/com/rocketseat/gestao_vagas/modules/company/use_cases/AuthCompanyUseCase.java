package com.rocketseat.gestao_vagas.modules.company.use_cases;

import com.rocketseat.gestao_vagas.exceptions.NotFoundException;
import com.rocketseat.gestao_vagas.modules.company.dto.AuthCompanyDto;
import com.rocketseat.gestao_vagas.modules.company.entities.CompanyEntity;
import com.rocketseat.gestao_vagas.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
public class AuthCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void execute(AuthCompanyDto authCompanyDto) throws AuthenticationException {
        var company = this.companyRepository
                .findByUsername(authCompanyDto.getUsername())
                .orElseThrow(() -> new NotFoundException("Empresa não encontrada."));

        var doesPasswordMatches = this.passwordEncoder.matches(authCompanyDto.getPassword(), company.getPassword());

        if (!doesPasswordMatches) {
            throw new AuthenticationException();
        }

        //TODO: Gerar token
    }

}
