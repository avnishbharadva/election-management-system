package com.ems.services;

import com.ems.dtos.CandidateByPartyDTO;
import com.ems.dtos.CandidateDTO;
import com.ems.dtos.CandidatePageResponse;
import com.ems.entities.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CandidateService {
    CandidateDTO findByCandidateSSN(String candidateSSN);
    Candidate saveCandidate(String candidateData, MultipartFile candidateImage, MultipartFile candidateSignature) throws IOException;
    CandidateDTO findById(Long id);
    Candidate update(Long candidateId,CandidateDTO candidateDTO);
    List<CandidateByPartyDTO> findByPartyName(String candidatePartyName);

    List<CandidateDTO> findAll();
    void deleteCandidateByCandidateId(Long candidateId);

    Page<CandidateDTO> getPagedCandidate(int page, int perPage, Sort sort);
    CandidatePageResponse getCandidateByElectionId(Long electionId, int page, int perPage);


}
