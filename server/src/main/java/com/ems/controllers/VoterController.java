package com.ems.controllers;

import com.ems.dtos.VoterRegisterDTO;
import com.ems.dtos.VoterSearchDTO;
import com.ems.dtos.VoterStatusDTO;
import com.ems.exceptions.DataNotFoundException;
import com.ems.services.VoterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.VoterDTO;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/voters")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class VoterController {
    private final VoterService voterService;

//    @PostMapping("/register")
//    public ResponseEntity<VoterDTO> registerVoter(@RequestBody VoterRegisterDTO voterRegisterDTO) {
//        VoterDTO registeredVoter = voterService.register(voterRegisterDTO);
//        return ResponseEntity.status(HttpStatus.CREATED).body(registeredVoter);
//    }

//    @GetMapping("/search")
//    public ResponseEntity<Page<VoterDTO>> searchVoters(
//            @RequestParam(required = false) String firstName,
//            @RequestParam(required = false) String lastName,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth,
//            @RequestParam(required = false) String dmvNumber,
//            @RequestParam(required = false) String ssnNumber,
//            @RequestParam(required = false) String city,
//            @RequestParam int page,
//            @RequestParam int size,
//            @RequestParam(required = false) String[] sort) {
//
//        VoterSearchDTO searchDTO = new VoterSearchDTO(firstName, lastName, dateOfBirth, dmvNumber, ssnNumber, city);
//        Page<VoterDTO> result = voterService.searchVoters(searchDTO, page, size, sort);
//        return ResponseEntity.ok(result);
//    }
//
//    @PatchMapping(value = "/{voterId}")
//    public ResponseEntity<VoterDTO> updateVoterImage(
//            @PathVariable String voterId,
//            @RequestPart(value = "voter", required = false) org.openapitools.model.VoterDTO voterDTO)
//            throws IOException {
//        return ResponseEntity.ok(voterService.updateVoter(voterId, voterDTO));
//    }
//
//
//    @GetMapping("/status")
//    public ResponseEntity<List<VoterStatusDTO>> getAllStatus(){
//        return ResponseEntity.ok(voterService.getAllStatus());
//    }
}
