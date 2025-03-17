package com.ems.controllers;

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
        log.info("Starting party creation: {}", partyDTO.getPartyId());
        PartyDTO createdParty = partyService.saveParty(partyDTO);
        log.info("Party created successfully: {}", createdParty.getPartyId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdParty);
    }

    @Override
    public ResponseEntity<PartyDTO> findByPartyId(Long partyId) {
        log.info("Fetching party details for ID: {}", partyId);
        PartyDTO party = partyService.partyById(partyId);
        log.info("Successfully fetched party details for ID: {}", partyId);
        return ResponseEntity.ok(party);
    }

    @Override
    public ResponseEntity<List<PartyDTO>> findAllParties() {
        log.info("Fetching all parties.");
        List<PartyDTO> parties = partyService.findAll();
        log.info("Successfully fetched {} parties.", parties.size());
        return ResponseEntity.ok(parties);
    }
}
