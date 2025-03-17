package com.ems.controllers;

import com.ems.services.CandidateService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.CandidatesApi;
import org.openapitools.model.CandidateDTO;
import org.openapitools.model.CandidateUpdateDTO;
import org.openapitools.model.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Data
@RestController
public class CandidateController implements CandidatesApi{

    private final CandidateService candidateService;

    @Override
    public ResponseEntity<ResponseDTO> createCandidate(CandidateDTO candidateDTO) {
        log.info("Process start to create candidate");
        return ResponseEntity.ok(candidateService.saveCandidate(candidateDTO));
    }

    @Override
    public ResponseEntity<ResponseDTO> deleteById(Long candidateId) {
        return ResponseEntity.ok(candidateService.deleteCandidateByCandidateId(candidateId));
    }

    @Override
    public ResponseEntity<ResponseDTO> getCandidateById(Long candidateId) {
        return ResponseEntity.ok(candidateService.findById(candidateId));
    }

    @Override
    public ResponseEntity<ResponseDTO> getCandidateBySSN(String candidateSSN) {
        return ResponseEntity.ok(candidateService.findByCandidateSSN(candidateSSN));
    }

    @Override
    public ResponseEntity<ResponseDTO> getCandidates(Integer page, Integer perPage, String sortBy, String sortDir) {
        return ResponseEntity.ok(candidateService.getPagedCandidate(page,perPage,sortBy,sortDir));
    }

    @Override
    public ResponseEntity<ResponseDTO> updateCandidate(Long candidateId, CandidateUpdateDTO candidateDTO) {
        return ResponseEntity.ok(candidateService.update(candidateId,candidateDTO));
    }
}
