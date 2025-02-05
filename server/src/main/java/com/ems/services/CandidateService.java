package com.ems.services;

import com.ems.dtos.CandidateDTO;
import com.ems.entities.Candidate;
import com.ems.entities.CandidateAddress;

public interface CandidateService {
    CandidateDTO findByCandidateSSN(String candidateSSN);
    Candidate save(CandidateDTO candidateDTO);


}
