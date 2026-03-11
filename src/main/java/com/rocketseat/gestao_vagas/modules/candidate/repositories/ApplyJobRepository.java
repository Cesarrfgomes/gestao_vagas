package com.rocketseat.gestao_vagas.modules.candidate.repositories;

import com.rocketseat.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyJobRepository extends JpaRepository<ApplyJobEntity,Long> {
}
