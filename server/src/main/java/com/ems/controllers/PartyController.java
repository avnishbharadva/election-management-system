package com.ems.controllers;

import com.ems.services.PartyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.PartyApi;
import org.openapitools.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PartyController implements PartyApi {

    private final PartyService partyService;

    @Override
    public ResponseEntity<PartyDTO> createParty(PartyRegisterDTO partyDataDTO) {
        log.info("Create party method from party controller called for - {}",partyDataDTO);
        return new ResponseEntity<>(new PartyDTO(
                "Party registered successfully",
                partyService.saveParty(partyDataDTO)
        ), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PartyDTO> deleteParty(Long partyId) {
        log.info("party delete called for : {}", partyId);
        partyService.deleteParty(partyId);
        return new ResponseEntity<>(new PartyDTO(
                "Party deleted successfully with id - " + partyId,
                null
        ), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PartyListDTO> findAllParties() {
        log.info("find all parties method called from controller");
        return new ResponseEntity<>(new PartyListDTO(
                "All parties fetched successfully",
                partyService.findAll()
        ), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PartyDTO> findByPartyId(Long partyId) {
        log.info("find party by id for {}", partyId);
        return new ResponseEntity<>(new PartyDTO(
                "Successfully find party based on its id - " + partyId,
                partyService.partyById(partyId)
        ), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PartyDTO> updateParty(Long partyId, PartyUpdateDTO partyUpdateDTO) {
        log.info("update party for partyId - {}, partyDetails - {}", partyId,partyUpdateDTO);
        return new ResponseEntity<>(new PartyDTO(
                "Party updated id - " + partyId,
                partyService.updateParty(partyId, partyUpdateDTO)
        ), HttpStatus.OK);
    }
}
