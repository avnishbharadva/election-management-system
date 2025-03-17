package com.ems.services;

import org.openapitools.model.CandidateDTO;
import org.openapitools.model.CandidateUpdateDTO;
import org.openapitools.model.ResponseDTO;

public interface CandidateService {
    ResponseDTO findByCandidateSSN(String candidateSSN);
    ResponseDTO saveCandidate(org.openapitools.model.CandidateDTO candidateData);
    ResponseDTO findById(Long id);
    ResponseDTO update(Long candidateId, CandidateUpdateDTO candidateDTO);
    ResponseDTO deleteCandidateByCandidateId(Long candidateId);
    ResponseDTO getPagedCandidate(int page, int perPage,String sortBy, String sortDir);
}
