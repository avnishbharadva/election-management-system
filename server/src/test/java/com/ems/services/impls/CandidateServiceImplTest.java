package com.ems.services.impls;

import com.ems.entities.Candidate;
import com.ems.mappers.CandidateMapper;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.CandidateRepository;
import com.ems.repositories.ElectionRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CandidateServiceImplTest {
    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private GlobalMapper globalMapper;

    @Mock
    private CandidateMapper candidateMapper;

    @Mock
    private ElectionRepository electionRepository;

    private Candidate candidate;

}
