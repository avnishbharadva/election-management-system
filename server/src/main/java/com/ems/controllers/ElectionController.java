package com.ems.controllers;

import com.ems.services.ElectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.ElectionsApi;
import org.openapitools.model.ElectionDTO;
import org.openapitools.model.ElectionPageResponse;
import org.openapitools.model.ModelApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ElectionController implements ElectionsApi {

    private final ElectionService electionService;

    @Override
    public ResponseEntity<ModelApiResponse> createElection(ElectionDTO electionDTO) {
        log.info("Received request to create an election: {}", electionDTO);
        ModelApiResponse response = electionService.saveElection(electionDTO);
        log.info("Election created successfully: {}", response);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ModelApiResponse> getAllElections() {
        log.info("Fetching all elections");
        ModelApiResponse response = electionService.getAllElection();
        log.info("Fetched elections successfully");
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ModelApiResponse> deleteElection(Long electionId) {
        log.warn("Request to delete election with ID: {}", electionId);
        ModelApiResponse response = electionService.deleteElectionById(electionId);
        log.warn("Election deleted successfully: {}", electionId);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ModelApiResponse> getElectionById(Long electionId) {
        log.info("Fetching election with ID: {}", electionId);
        ModelApiResponse response = electionService.getElectionById(electionId);
        log.info("Election details retrieved: {}", response);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ElectionPageResponse> getSortedElection(String order, Integer page, Integer size) {
        log.info("Fetching sorted elections: order={}, page={}, size={}", order, page, size);
        ElectionPageResponse response = electionService.getElectionsSorted(order, page, size);
        log.info("Sorted elections retrieved successfully");
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ModelApiResponse> updateElection(Long electionId, ElectionDTO electionDTO) {
        log.info("Updating election with ID: {}", electionId);
        ModelApiResponse response = electionService.updateElection(electionId, electionDTO);
        log.info("Election updated successfully: {}", response);
        return ResponseEntity.ok(response);
    }
}
