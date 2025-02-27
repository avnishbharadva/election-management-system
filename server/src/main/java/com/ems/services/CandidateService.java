package com.ems.services;

import com.ems.dtos.*;
import com.ems.entities.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CandidateService {
    CandidateDetailsDTO findByCandidateSSN(String candidateSSN);
    Candidate saveCandidate(CandidateDTO candidateData, MultipartFile candidateImage, MultipartFile candidateSignature) throws IOException;
    CandidateDataDTO findById(Long id);
    Candidate update(Long candidateId, CandidateDTO candidateDTO, MultipartFile candidateImage, MultipartFile candidateSignature) throws IOException;
    List<CandidateByPartyDTO> findByPartyName(String candidatePartyName);
    void deleteCandidateByCandidateId(Long candidateId);
    Page<CandidateDetailsDTO> getPagedCandidate(int page, int perPage, Sort sort);
    CandidatePageResponse getCandidateByElectionId(Long electionId, int page, int perPage);
    Page<CandidateDTO> searchCandidates(CandidateDTO candidateDTO, int page, int perPage, Sort sort);
    List<CandidateDetailsDTO> getCandidateInfo();
}
