package com.ems.controllers;

import com.ems.services.ElectionService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.ElectionsApi;
import org.openapitools.model.ElectionDTO;
import org.openapitools.model.ElectionPageResponse;
import org.openapitools.model.ModelApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ElectionController implements ElectionsApi {

    private final ElectionService electionService;

    @Override
    public ResponseEntity<ModelApiResponse> createElection(ElectionDTO electionDTO) {
        return ResponseEntity.ok(electionService.saveElection(electionDTO));
    }

    @Override
    public ResponseEntity<ModelApiResponse> getAllElections() {
        return ResponseEntity.ok(electionService.getAllElection());
    }

    @Override
    public ResponseEntity<ModelApiResponse> deleteElection(Long electionId) {
        return ResponseEntity.ok(electionService.deleteElectionById(electionId));
    }

    @Override
    public ResponseEntity<ModelApiResponse> getElectionById(Long electionId) {
        return ResponseEntity.ok(electionService.getElectionById(electionId));
    }

    @Override
    public ResponseEntity<ElectionPageResponse> getSortedElection(String order, Integer page, Integer size) {
        return ResponseEntity.ok(electionService.getElectionsSorted(order, page, size));
    }

    @Override
    public ResponseEntity<ModelApiResponse> updateElection(Long electionId, ElectionDTO electionDTO) {
        return ResponseEntity.ok(electionService.updateElection(electionId, electionDTO));
    }
}
