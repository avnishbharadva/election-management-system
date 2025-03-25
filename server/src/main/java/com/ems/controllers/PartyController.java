package com.ems.controllers;

import com.ems.services.PartyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.PartyApi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PartyController implements PartyApi {

    private final PartyService partyService;

    @Override
    public ResponseEntity<org.openapitools.model.PartyDTO> createParty(org.openapitools.model.PartyRegisterDTO partyDataDTO) {
        log.info("Create party method from party controller called for - {}",partyDataDTO);
        return new ResponseEntity<>(new org.openapitools.model.PartyDTO(
                "Party registered successfully",
                partyService.saveParty(partyDataDTO)
        ), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<org.openapitools.model.PartyDTO> deleteParty(Long partyId) {
        log.info("party delete called for : {}", partyId);
        partyService.deleteParty(partyId);
        return new ResponseEntity<>(new org.openapitools.model.PartyDTO(
                "Party deleted successfully with id - " + partyId,
                null
        ), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<org.openapitools.model.PartyListDTO> findAllParties() {
        log.info("find all parties method called from controller");
        return new ResponseEntity<>(new org.openapitools.model.PartyListDTO(
                "All parties fetched successfully",
                partyService.findAll()
        ), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<org.openapitools.model.PartyDTO> findByPartyId(Long partyId) {
        log.info("find party by id for {}", partyId);
        return new ResponseEntity<>(new org.openapitools.model.PartyDTO(
                "Successfully find party based on its id - " + partyId,
                partyService.partyById(partyId)
        ), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<org.openapitools.model.PartyDTO> updateParty(Long partyId, org.openapitools.model.PartyUpdateDTO partyUpdateDTO) {
        log.info("update party for partyId - {}, partyDetails - {}", partyId,partyUpdateDTO);
        return new ResponseEntity<>(new org.openapitools.model.PartyDTO(
                "Party updated id - " + partyId,
                partyService.updateParty(partyId, partyUpdateDTO)
        ), HttpStatus.OK);
    }
}
