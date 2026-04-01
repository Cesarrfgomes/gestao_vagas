package com.rocketseat.gestao_vagas.modules.company.entities;

import com.rocketseat.gestao_vagas.shared.entities.UserBaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity(name = "company")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CompanyEntity extends UserBaseEntity {

    private String website;
}