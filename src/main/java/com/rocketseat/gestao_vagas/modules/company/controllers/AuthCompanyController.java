package com.rocketseat.gestao_vagas.modules.company.controllers;

import com.rocketseat.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import com.rocketseat.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.naming.AuthenticationException;

@RequestMapping("/auth")
@Tag(name = "Empresa")
public interface AuthCompanyController {


    @PostMapping("/company")
    @Operation(summary = "Autenticação de empresa")
    ResponseEntity<AuthCompanyResponseDTO> auth(@RequestBody AuthCompanyDTO authCompanyDto) throws AuthenticationException;
}