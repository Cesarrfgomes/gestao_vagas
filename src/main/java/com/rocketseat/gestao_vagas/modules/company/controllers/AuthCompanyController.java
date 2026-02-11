package com.rocketseat.gestao_vagas.modules.company.controllers;

import com.rocketseat.gestao_vagas.modules.company.dto.AuthCompanyDto;
import com.rocketseat.gestao_vagas.modules.company.dto.AuthCompanyResponseDto;
import com.rocketseat.gestao_vagas.modules.company.use_cases.AuthCompanyUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/auth")
public class AuthCompanyController {

    @Autowired
    private AuthCompanyUseCase authCompanyUseCase;

    @PostMapping("/company")
    public ResponseEntity<AuthCompanyResponseDto> auth(@RequestBody AuthCompanyDto authCompanyDto) throws AuthenticationException {
        String token = this.authCompanyUseCase.execute(authCompanyDto);

        return ResponseEntity.ok(new AuthCompanyResponseDto(token));
    }
}
