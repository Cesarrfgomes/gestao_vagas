package com.rocketseat.gestao_vagas.modules.candidate.entities;

import com.rocketseat.gestao_vagas.shared.entities.UserBaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity(name = "candidate")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CandidateEntity extends UserBaseEntity {

    private String curriculum;
}