package com.rocketseat.gestao_vagas.modules.company.controllers;

import com.rocketseat.gestao_vagas.modules.company.dto.AuthCompanyDto;
import com.rocketseat.gestao_vagas.modules.company.dto.AuthCompanyResponseDto;
import com.rocketseat.gestao_vagas.modules.company.use_cases.AuthCompanyUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
public class AuthCompanyControllerImpl implements AuthCompanyController{

    @Autowired
    private AuthCompanyUseCase authCompanyUseCase;

    @Override
    public ResponseEntity<AuthCompanyResponseDto> auth(AuthCompanyDto authCompanyDto) throws AuthenticationException {
        String token = this.authCompanyUseCase.execute(authCompanyDto);

        return ResponseEntity.ok(new AuthCompanyResponseDto(token));
    }
}
