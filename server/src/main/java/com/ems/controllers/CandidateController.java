package com.ems.controllers;

import com.ems.dtos.*;
import com.ems.entities.Candidate;
import com.ems.exceptions.DataNotFoundException;
import com.ems.services.CandidateService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/candidates")
public class CandidateController {

    private final CandidateService candidateService;

    @GetMapping("/by-ssn/{candidateSSN}")
    ResponseEntity<CandidateDetailsDTO> getCandidateBySSN(@Valid @PathVariable String candidateSSN) {
        log.info("Fetching candidate by SSN: {}", candidateSSN);
        try {
            var candidateDTO = candidateService.findByCandidateSSN(candidateSSN);
            log.info("Candidate found: {}", candidateDTO);
            return ResponseEntity.ok(candidateDTO);
        } catch (DataNotFoundException e) {
            log.warn("No candidate found with SSN: {}", candidateSSN);
            throw new DataNotFoundException("No candidate found");
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> createCandidate(@RequestBody CandidateDTO candidateData) {
        log.info("Attempting to register a new candidate: {}", candidateData);
        try {
            Candidate savedCandidate = candidateService.saveCandidate(candidateData);
            log.info("Candidate registered successfully: {}", savedCandidate);
            return ResponseEntity.ok(
                    new ResponseDTO("Candidate added successfully", savedCandidate, LocalDateTime.now(), true)
            );
        } catch (IOException e) {
            log.error("Error while adding candidate", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO("Error while adding candidate", null, LocalDateTime.now(), false));
        }
    }

    @GetMapping("/{candidateId}")
    ResponseEntity<CandidateDTO> getCandidateById(@PathVariable Long candidateId) {
        log.info("Fetching candidate by ID: {}", candidateId);
        return ResponseEntity.ok().body(candidateService.findById(candidateId));
    }

    @PutMapping(value = "/{candidateId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> updateCandidate(
            @PathVariable Long candidateId,
            @RequestBody CandidateDTO candidateDTO) {

        log.info("Updating candidate ID: {}", candidateId);

        if (candidateDTO == null) {
            log.warn("Update request failed: No candidate data provided");
            return ResponseEntity.badRequest()
                    .body(new ResponseDTO("Candidate data must be provided", null, LocalDateTime.now(), false));
        }

        try {
            Candidate updatedCandidate = candidateService.update(candidateId, candidateDTO);
            log.info("Candidate updated successfully: {}", updatedCandidate);
            return ResponseEntity.ok(
                    new ResponseDTO("Candidate updated successfully", updatedCandidate, LocalDateTime.now(), true)
            );
        } catch (DataNotFoundException e) {
            log.error("Candidate not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(e.getMessage(), null, LocalDateTime.now(), false));
        } catch (Exception e) {
            log.error("Unexpected error while updating candidate: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO("Error while updating candidate", null, LocalDateTime.now(), false));
        }
    }

    @GetMapping("/by-party/{candidatePartyName}")
    public ResponseEntity<ResponseDTO> getCandidateByPartyName(@PathVariable String candidatePartyName) {
        log.info("Fetching candidates for party: {}", candidatePartyName);
        List<CandidateByPartyDTO> candidates = candidateService.findByPartyName(candidatePartyName);

        if (candidates.isEmpty()) {
            log.warn("No candidates found for party: {}", candidatePartyName);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO("No candidates found for the given party name", Collections.emptyList(), LocalDateTime.now(), false));
        }

        log.info("Candidates retrieved successfully for party: {}", candidatePartyName);
        return ResponseEntity.ok(
                new ResponseDTO("Candidates retrieved successfully", candidates, LocalDateTime.now(), true)
        );
    }

    @GetMapping
    public ResponseEntity<CandidatePageResponse> getCandidates(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "perPage", defaultValue = "10") int perPage,
            @RequestParam(value = "sortBy", defaultValue = "candidateId") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir
    ) {
        log.info("Fetching candidates: page={}, perPage={}, sortBy={}, sortDir={}", page, perPage, sortBy, sortDir);

        if (page < 0 || perPage <= 0) {
            log.warn("Invalid pagination parameters: page={}, perPage={}", page, perPage);
            throw new IllegalArgumentException("Page and perPage must be positive.");
        }

        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Page<CandidateDetailsDTO> candidatePage = candidateService.getPagedCandidate(page, perPage, sort);

        if (candidatePage.isEmpty()) {
            log.warn("No candidates found.");
            throw new DataNotFoundException("No candidates found.");
        }

        log.info("Candidates retrieved successfully, total: {}", candidatePage.getTotalElements());
        return ResponseEntity.ok(new CandidatePageResponse(
                candidatePage.getContent(),
                candidatePage.getNumber(),
                candidatePage.getTotalPages(),
                candidatePage.getTotalElements(),
                candidatePage.getSize()
        ));
    }

    @DeleteMapping("/{candidateId}")
    public ResponseEntity<ResponseDTO> deleteById(@PathVariable Long candidateId) {
        log.info("Deleting candidate ID: {}", candidateId);
        try {
            candidateService.deleteCandidateByCandidateId(candidateId);
            log.info("Candidate deleted successfully: {}", candidateId);
            return ResponseEntity.ok(
                    new ResponseDTO("Candidate deleted successfully", null, LocalDateTime.now(), true)
            );
        } catch (Exception e) {
            log.error("Candidate deletion failed, ID: {}", candidateId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO("No Candidate Found", null, LocalDateTime.now(), false));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseDTO> searchCandidates(
            @RequestBody CandidateDTO searchCriteria,
            @RequestParam int page,
            @RequestParam(defaultValue = "candidateId") String sortBy,
            @RequestParam int perPage,
            @RequestParam(defaultValue = "ASC") String sortOrder) {

        log.info("Searching candidates with criteria: {}, page={}, perPage={}, sortBy={}, sortOrder={}",
                searchCriteria, page, perPage, sortBy, sortOrder);

        try {
            Sort sort = Sort.by(Sort.Order.by(sortBy).with(Sort.Direction.fromString(sortOrder)));
            Page<CandidateDTO> candidatesPage = candidateService.searchCandidates(searchCriteria, page, perPage, sort);

            if (candidatesPage.isEmpty()) {
                log.warn("No candidates found matching the search criteria.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO("No candidates found matching the criteria", Collections.emptyList(), LocalDateTime.now(), false));
            }

            log.info("Candidates retrieved successfully, total: {}", candidatesPage.getTotalElements());
            return ResponseEntity.ok(
                    new ResponseDTO("Candidates retrieved successfully", candidatesPage, LocalDateTime.now(), true)
            );
        } catch (Exception e) {
            log.error("Error while searching candidates", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO("Error while searching candidates", null, LocalDateTime.now(), false));
        }
    }
}
