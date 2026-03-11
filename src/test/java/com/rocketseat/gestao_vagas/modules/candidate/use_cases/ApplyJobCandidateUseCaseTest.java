package com.rocketseat.gestao_vagas.modules.candidate.use_cases;

import com.rocketseat.gestao_vagas.exceptions.NotFoundException;
import com.rocketseat.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import com.rocketseat.gestao_vagas.modules.candidate.entities.CandidateEntity;
import com.rocketseat.gestao_vagas.modules.candidate.repositories.ApplyJobRepository;
import com.rocketseat.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import com.rocketseat.gestao_vagas.modules.company.entities.JobEntity;
import com.rocketseat.gestao_vagas.modules.company.repositories.JobRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {

    @InjectMocks
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private ApplyJobRepository applyJobRepository;

    @Test
    @DisplayName("Should not be able to apply job with candidate not found")
    public void shouldNotBeAbleToApplyJobWithCandidateNotFound() {
        try {
            applyJobCandidateUseCase.execute(null, null);
        } catch (NotFoundException e) {
            assertThat(e).isInstanceOf(NotFoundException.class);
        }
    }

    @Test
    @DisplayName("Should not be able to apply job with job not found")
    public void shouldNotBeAbleToApplyJobWithJobNotFound() {
        var id = UUID.randomUUID();
        when(candidateRepository.findById(id)).thenReturn(Optional.empty());
        try {
            applyJobCandidateUseCase.execute(id, null);
        } catch (NotFoundException e) {
            assertThat(e).isInstanceOf(NotFoundException.class);
        }
    }

    @Test
    @DisplayName("Should be able to apply job")
    public void shouldBeAbleToApplyJob() {
        var candidateId = UUID.randomUUID();
        var jobId = UUID.randomUUID();

        var applyJobCreated = ApplyJobEntity.builder()
                .id(UUID.randomUUID())
                .candidateId(candidateId)
                .jobId(jobId)
                .build();

        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(new CandidateEntity()));
        when(jobRepository.findById(jobId)).thenReturn(Optional.of(new JobEntity()));

        when(applyJobRepository.save(any(ApplyJobEntity.class))).thenReturn(applyJobCreated);

        var result = applyJobCandidateUseCase.execute(candidateId, jobId);

        assertThat(result).isNotNull();
        assertThat(result).hasFieldOrProperty("id");
        assertThat(result.getId()).isEqualTo(applyJobCreated.getId());

        verify(applyJobRepository, times(1)).save(any(ApplyJobEntity.class));
    }
}
