package com.ems.services;

import com.ems.dtos.CandidateDTO;
import com.ems.entities.Candidate;
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
        Candidate candidate = candidateRepository.findByCandidateSSN(candidateSSN)
                .orElse(null);
        if(candidate==null)
        {
            return null;
        }
        return candidateMapper.toCandidateDTO(candidate);
    }
}
