package com.ems.controllers;

<<<<<<< HEAD
import com.ems.dtos.PartyDTO;
import com.ems.services.PartyService;
import com.ems.services.VoterReportServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/party")
public class PartyController {
    private final PartyService partyService;
    private final VoterReportServiceImpl reportService;

//    @PostMapping
//    public ResponseEntity<Party> createParty(@Valid @RequestBody PartyDTO partyDTO) throws IOException {
//        return ResponseEntity.ok(partyService.saveParty(partyDTO));
//    }


//    @GetMapping("/{partyId}")
//    public ResponseEntity<PartyDTO> findByPartyId(@PathVariable long partyId){
//        return ResponseEntity.ok(partyService.partyById(partyId));
//    }

//    @GetMapping
//    public ResponseEntity<List<PartyDTO>> findAllParties(){
//        return ResponseEntity.ok(partyService.findAll());
//    }

    @GetMapping("/{format}")
    public String generateReportApi(@PathVariable String format) throws JRException, FileNotFoundException {
        log.info("Data : {}", format);
        reportService.generateReport(format);
        return "Created...";
    }

=======
import com.ems.services.PartyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.PartyApi;
import org.openapitools.model.*;
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
>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511
}
