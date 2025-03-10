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
        PartyDTO createdParty = partyService.saveParty(partyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdParty);
    }

    @Override
    public ResponseEntity<PartyDTO> findByPartyId(Long partyId) {
            return ResponseEntity.ok(partyService.partyById(partyId));
    }

    @Override
    public ResponseEntity<List<PartyDTO>> findAllParties() {
        return ResponseEntity.ok(partyService.findAll());
    }
}
