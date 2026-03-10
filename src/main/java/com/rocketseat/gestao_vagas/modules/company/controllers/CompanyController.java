package com.rocketseat.gestao_vagas.modules.company.controllers;

import com.rocketseat.gestao_vagas.exceptions.AlreadyExistsException;
import com.rocketseat.gestao_vagas.modules.company.entities.CompanyEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/company")
@Tag(name = "Empresa")
public interface CompanyController {


    @PostMapping
    @Operation(summary = "Cadastro de empresas")
    ResponseEntity<CompanyEntity> create(@Valid @RequestBody CompanyEntity companyEntity) throws AlreadyExistsException;
}
