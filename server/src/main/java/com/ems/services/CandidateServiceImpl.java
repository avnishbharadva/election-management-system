package com.ems.services;

import com.ems.dtos.CandidateDTO;
import com.ems.entities.Candidate;
import com.ems.exceptions.CandidateNotFoundException;
import com.ems.mappers.CandidateMapper;
import com.ems.repositories.CandidateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CandidateServiceImpl implements CandidateService{
    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;

    @Override
    public CandidateDTO findByCandidateSSN(String candidateSSN) {
        Optional<Candidate> candidate = candidateRepository.findByCandidateSSN(candidateSSN);
        if (candidate.isPresent())
        {
            return candidateMapper.toCandidateDTO(candidate.get());
        }
        else {
            throw new CandidateNotFoundException("No Candidate is found with the SSN:"+candidateSSN);
        }

    }
}
