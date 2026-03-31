package com.rocketseat.gestao_vagas.modules.company.controllers;


import com.rocketseat.gestao_vagas.exceptions.NotFoundException;
import com.rocketseat.gestao_vagas.modules.company.dto.CreateJobDTO;
import com.rocketseat.gestao_vagas.modules.company.entities.CompanyEntity;
import com.rocketseat.gestao_vagas.modules.company.repositories.CompanyRepository;
import com.rocketseat.gestao_vagas.modules.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;


@RunWith(value = SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class CreateJobControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CompanyRepository companyRepository;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void shouldBeAbleToCreateANewJob() throws Exception {

        var company = CompanyEntity.builder()
                .name("COMPANY_TEST")
                .email("email@company.com")
                .username("COMPANY_TEST")
                .password("1234567890")
                .description("COMPANY_TEST")
                .build();

        company = companyRepository.saveAndFlush(company);

        CreateJobDTO createJobDto = CreateJobDTO.builder()
                .benefits("BENEFITS_TEST")
                .description("DESCRIPTION")
                .level("LEVEL").
                build();

        var token = TestUtils.generateCompanyToken(company);

        mvc.perform(MockMvcRequestBuilders
                .post("/company/job/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(createJobDto))
                .header("Authorization", token)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldNotBeAbleToCreateANewJobWithoutACompany() throws Exception {

        var company = CompanyEntity.builder()
                .id(UUID.randomUUID())
                .name("COMPANY_TEST")
                .email("email@company.com")
                .username("COMPANY_TEST")
                .password("1234567890")
                .description("COMPANY_TEST")
                .build();

        CreateJobDTO createJobDto = CreateJobDTO.builder()
                .benefits("BENEFITS_TEST")
                .description("DESCRIPTION")
                .level("LEVEL").
                build();

        var token = TestUtils.generateCompanyToken(company);

        mvc.perform(MockMvcRequestBuilders
                .post("/company/job/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(createJobDto))
                .header("Authorization", token)
        ).andExpect(result -> assertInstanceOf(NotFoundException.class, result.getResolvedException()));
    }
}
