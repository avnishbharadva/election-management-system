package com.ems.controllers;

import  com.ems.dtos.*;
import com.ems.entities.Candidate;
import com.ems.exceptions.DataNotFoundException;
import com.ems.services.CandidateService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/candidate")
public class CandidateController {

    private final CandidateService candidateService;
    @GetMapping("/getAllDetails")
    public ResponseEntity<ResponseDTO> getAllCandidateDetails() {
        List<CandidateDetailsDTO> candidateDetailsList = candidateService.getCandidateInfo();
        if (candidateDetailsList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO("No candidate details found", Collections.emptyList(), LocalDateTime.now(), false));
        }
        return ResponseEntity.ok(
                new ResponseDTO("Candidate details retrieved successfully", candidateDetailsList, LocalDateTime.now(), true)
        );
    }



    @GetMapping("/ssn/{candidateSSN}")
    ResponseEntity<CandidateDetailsDTO> getCandidateBySSN(@Valid @PathVariable String candidateSSN)
    {
        try{
            var candidateDTO= candidateService.findByCandidateSSN(candidateSSN);
            return ResponseEntity.ok(candidateDTO);
        }
        catch (DataNotFoundException e){
            throw new DataNotFoundException("No candidate found");
        }
    }

    @PostMapping(value = "/addCandidate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDTO> createCandidate(
            @RequestPart("candidate") CandidateDTO candidateData,
            @RequestPart(value = "candidateImage", required = false) MultipartFile candidateImage,
            @RequestPart(value = "candidateSignature", required = false) MultipartFile candidateSignature) {
        try {
            Candidate savedCandidate = candidateService.saveCandidate(candidateData, candidateImage, candidateSignature);
            return ResponseEntity.ok(
                    new ResponseDTO("Candidate added successfully", savedCandidate, LocalDateTime.now(), true)
            );
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO("Error while adding candidate", null, LocalDateTime.now(), false));
        }
    }

    @GetMapping("/candidateId/{candidateId}")
    ResponseEntity<CandidateDataDTO> getCandidateById(@PathVariable Long candidateId){
        return ResponseEntity.ok().body( candidateService.findById(candidateId));
    }

    @PutMapping(value = "/updateCandidate/{candidateId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDTO> updateCandidate(
            @PathVariable Long candidateId,
            @ModelAttribute CandidateDTO candidateDTO,
            @RequestPart(value = "candidateImage", required = false) MultipartFile candidateImage,
            @RequestPart(value = "candidateSignature", required = false) MultipartFile candidateSignature) {

        if (candidateDTO == null) {
            return ResponseEntity.badRequest()
                    .body(new ResponseDTO("Candidate data must be provided", null, LocalDateTime.now(), false));
        }

        try {
            Candidate updatedCandidate = candidateService.update(candidateId, candidateDTO, candidateImage, candidateSignature);
            return ResponseEntity.ok(
                    new ResponseDTO("Candidate updated successfully", updatedCandidate, LocalDateTime.now(), true)
            );
        } catch (IOException e) {
            throw new DataNotFoundException("No such candidate with the id:"+candidateId);
        }
    }


    @GetMapping("/partyName/{candidatePartyName}")
    public ResponseEntity<ResponseDTO> getCandidateByPartyName(@PathVariable String candidatePartyName) {
        List<CandidateByPartyDTO> candidates = candidateService.findByPartyName(candidatePartyName);

        if (candidates.isEmpty()) {
            throw new DataNotFoundException("No candidate found for the given party:"+candidatePartyName);
        }

        return ResponseEntity.ok(
                new ResponseDTO("Candidates retrieved successfully", candidates, LocalDateTime.now(), true)
        );
    }

    @GetMapping("/paged")
    public ResponseEntity<CandidatePageResponse> getCandidates(
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "perPage",defaultValue = "10") int perPage,
            @RequestParam(value = "sortBy",defaultValue = "candidateId") String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc") String sortDir
    ) {
        if (page < 0 || perPage <= 0) {
            throw new IllegalArgumentException("Page and perPage must be positive.");
        }

        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Page<CandidateDetailsDTO> candidatePage = candidateService.getPagedCandidate(page, perPage, sort);

        if (candidatePage.isEmpty()) {
            throw new DataNotFoundException("No candidates found.");
        }

        return ResponseEntity.ok(new CandidatePageResponse(
                candidatePage.getContent(),
                candidatePage.getNumber(),
                candidatePage.getTotalPages(),
                candidatePage.getTotalElements(),
                candidatePage.getSize()
        ));
    }
    @GetMapping("election/{electionId}")
    public ResponseEntity<CandidatePageResponse> getCandidateByElection(@PathVariable Long electionId,
                                                     @RequestParam(value = "page", defaultValue = "0") int page,
                                                     @RequestParam(value = "perPage", defaultValue = "10") int perPage){
        if (page < 0 || perPage <= 0) {
            throw new IllegalArgumentException("Page and perPage must be non-negative and greater than zero.");
        }

        CandidatePageResponse candidates = candidateService.getCandidateByElectionId(electionId, page, perPage);
        return ResponseEntity.ok(candidates);
    }

    @DeleteMapping("/delete/{candidateId}")
    public ResponseEntity<ResponseDTO> deleteById(@PathVariable Long candidateId) {
        try {
            candidateService.deleteCandidateByCandidateId(candidateId);
            return ResponseEntity.ok(
                    new ResponseDTO("Candidate deleted successfully", null, LocalDateTime.now(), true)
            );
        } catch (Exception e) {
            throw new DataNotFoundException("No candidate with id:"+candidateId+" found");
        }
    }


    @GetMapping("/search")
    public ResponseEntity<ResponseDTO> searchCandidates(
            @RequestBody CandidateDTO searchCriteria,
            @RequestParam int page,
            @RequestParam(defaultValue = "candidateId") String sortBy,
            @RequestParam int perPage,
            @RequestParam(defaultValue = "ASC") String sortOrder) {

        try {
            Sort sort = Sort.by(Sort.Order.by(sortBy).with(Sort.Direction.fromString(sortOrder)));
            Page<CandidateDTO> candidatesPage = candidateService.searchCandidates(searchCriteria, page, perPage, sort);

            if (candidatesPage.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO("No candidates found matching the criteria", Collections.emptyList(), LocalDateTime.now(), false));
            }

            return ResponseEntity.ok(
                    new ResponseDTO("Candidates retrieved successfully", candidatesPage, LocalDateTime.now(), true)
            );
        } catch (Exception e) {
            throw new DataNotFoundException("No candidates found");
        }
    }

}
