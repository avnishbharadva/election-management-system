package com.ems.services;

import org.openapitools.model.CandidateDto;
import org.openapitools.model.CandidateUpdateDto;
import org.openapitools.model.ResponseDto;

public interface CandidateService {
    ResponseDto findByCandidateSSN(String candidateSSN);
    ResponseDto saveCandidate(org.openapitools.model.CandidateDto candidateData);
    ResponseDto findById(Long id);
    ResponseDto update(Long candidateId, CandidateUpdateDto candidateDto);
    ResponseDto deleteCandidateByCandidateId(Long candidateId);
    ResponseDto getPagedCandidate(int page, int perPage,String sortBy, String sortDir);
}
