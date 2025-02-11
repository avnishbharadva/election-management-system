package com.ems.services;

import com.ems.dtos.CandidateByPartyDTO;
import com.ems.dtos.CandidateDTO;
import com.ems.entities.Candidate;
import org.hibernate.query.Page;

import java.util.List;

public interface CandidateService {
    CandidateDTO findByCandidateSSN(String candidateSSN);
    Candidate saveCandidate(CandidateDTO candidateDTO);
    CandidateDTO findById(Long id);
    Candidate update(Long candidateId,CandidateDTO candidateDTO);
    List<CandidateByPartyDTO> findByPartyName(String candidatePartyName);
    List<CandidateDTO> findAll();

}
