package com.rocketseat.gestao_vagas.modules.company.use_cases;

import com.rocketseat.gestao_vagas.exceptions.NotFoundException;
import com.rocketseat.gestao_vagas.modules.company.entities.CompanyEntity;
import com.rocketseat.gestao_vagas.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindCompanyById {
    @Autowired
    private CompanyRepository companyRepository;

    public CompanyEntity execute(UUID companyId) {
        return this.companyRepository
                .findById(companyId)
                .orElseThrow(() -> new NotFoundException("Empresa não encontrada."));
    }
}
