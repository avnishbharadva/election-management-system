package com.ems.controllers;

import com.ems.exceptions.DataNotFoundException;
import com.ems.exceptions.DataAlreadyExistException;
import com.ems.services.PartyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.PartyApi;
import org.openapitools.model.PartyDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PartyApiController implements PartyApi {

    private final PartyService partyService;

    @Override
    public ResponseEntity<PartyDTO> createParty(PartyDTO partyDTO) {
        try {
            PartyDTO createdParty = partyService.saveParty(partyDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdParty);
        } catch (DataAlreadyExistException e) {
            log.warn("Party already exists: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception e) {
            log.error("Error creating party: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<PartyDTO> findByPartyId(Long partyId) {
        try {
            PartyDTO party = partyService.partyById(partyId);
            return ResponseEntity.ok(party);
        } catch (DataNotFoundException e) {
            log.warn("Party not found for ID {}: {}", partyId, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            log.error("Error retrieving party with ID {}: {}", partyId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<List<PartyDTO>> findAllParties() {
        try {
            List<PartyDTO> parties = partyService.findAll();
            if (parties.isEmpty()) {
                throw new DataNotFoundException("No parties found.");
            }
            return ResponseEntity.ok(parties);
        } catch (DataNotFoundException e) {
            log.warn("No parties found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            log.error("Error fetching all parties: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
