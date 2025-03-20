package com.ems.controllers;

import com.ems.services.CandidateService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.CandidatesApi;
import org.openapitools.model.CandidateDto;
import org.openapitools.model.CandidateUpdateDto;
import org.openapitools.model.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Data
@RestController
<<<<<<< HEAD
@AllArgsConstructor
@RequestMapping("/api/candidates")
public class CandidateController {
=======
public class CandidateController implements CandidatesApi{
>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511

    private final CandidateService candidateService;

    @Override
    public ResponseEntity<ResponseDto> createCandidate(CandidateDto candidateDto) {
        log.debug("Create candidate with firstName:{},lastName:{},SSN:{}",candidateDto.getCandidateName().getFirstName(),candidateDto.getCandidateName().getLastName(),candidateDto.getCandidateSSN());
        return ResponseEntity.ok(candidateService.saveCandidate(candidateDto));
    }

    @Override
    public ResponseEntity<ResponseDto> deleteById(Long candidateId) {
        log.info("Remove Candidate With candidateId:{}",candidateId);
        return ResponseEntity.ok(candidateService.deleteCandidateByCandidateId(candidateId));
    }

    @Override
    public ResponseEntity<ResponseDto> getCandidateById(Long candidateId) {
        log.info("fetch candidate with ID: {}", candidateId);
        return ResponseEntity.ok(candidateService.findById(candidateId));
    }

    @Override
    public ResponseEntity<ResponseDto> getCandidateBySSN(String candidateSSN) {
        log.info("fetch candidate details for SSN: {}", candidateSSN);
        return ResponseEntity.ok(candidateService.findByCandidateSSN(candidateSSN));
    }

    @Override
    public ResponseEntity<ResponseDto> getCandidates(Integer page, Integer perPage, String sortBy, String sortDir) {
        log.info("request to fetch candidates - Page: {}, Per Page: {}, Sort By: {}, Sort Direction: {}",
                page, perPage, sortBy, sortDir);
        return ResponseEntity.ok(candidateService.getPagedCandidate(page,perPage,sortBy,sortDir));
    }

    @Override
    public ResponseEntity<ResponseDto> updateCandidate(Long candidateId, CandidateUpdateDto candidateDto) {
        log.info("Request to Update Candidate - Id:{}",candidateId);
        return ResponseEntity.ok(candidateService.update(candidateId,candidateDto));
    }
}
