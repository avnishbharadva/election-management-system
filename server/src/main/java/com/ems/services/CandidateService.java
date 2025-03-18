package com.ems.services;

import com.ems.dtos.CandidateDTO;
import com.ems.dtos.CandidateDetailsDTO;
import com.ems.entities.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.io.IOException;

public interface CandidateService {
    CandidateDetailsDTO findByCandidateSSN(String candidateSSN);
    Candidate saveCandidate(CandidateDTO candidateData) throws IOException;
    CandidateDTO findById(Long id);
    Candidate update(Long candidateId, CandidateDTO candidateDTO) throws IOException;
    void deleteCandidateByCandidateId(Long candidateId);
    Page<CandidateDetailsDTO> getPagedCandidate(int page, int perPage, Sort sort);

}
