package com.rocketseat.gestao_vagas.modules.company.controllers;

import com.rocketseat.gestao_vagas.exceptions.AlreadyExistsException;
import com.rocketseat.gestao_vagas.modules.company.entities.CompanyEntity;
import com.rocketseat.gestao_vagas.modules.company.use_cases.CreateCompanyUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyControllerImpl implements CompanyController {

    @Autowired
    private CreateCompanyUseCase createCompanyUseCase;

    @Override
    public ResponseEntity<CompanyEntity> create(CompanyEntity companyEntity) throws AlreadyExistsException {
        var result = this.createCompanyUseCase.execute(companyEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
