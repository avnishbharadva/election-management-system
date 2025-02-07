package com.ems.services;

import com.ems.dtos.CandidateDTO;
import com.ems.entities.Candidate;

public interface CandidateService {
    CandidateDTO findByCandidateSSN(String candidateSSN);
    Candidate saveCandidate(CandidateDTO candidateDTO);
    CandidateDTO findById(Long id);
    Candidate update(Long candidateId,CandidateDTO candidateDTO);
}
