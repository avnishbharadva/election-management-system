package com.ems.controllers;

import com.ems.services.PartyService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.PartyApi;
import org.openapitools.model.PartyDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PartyApiController implements PartyApi {

    private final PartyService partyService;

    @Override
    public ResponseEntity<PartyDTO> createParty(PartyDTO partyDTO) {
        return ResponseEntity.ok(partyService.saveParty(partyDTO));
    }

    @Override
    public ResponseEntity<PartyDTO> findByPartyId(Long partyId) {
        return ResponseEntity.ok(partyService.partyById(partyId));
    }


}
