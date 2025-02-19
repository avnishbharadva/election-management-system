package com.ems.controllers;

import com.ems.dtos.VoterRegisterDTO;
import com.ems.dtos.VoterUpdateDTO;
import com.ems.entities.Voter;
import com.ems.exceptions.PartyNotFoundException;
import com.ems.repositories.VoterRepository;
import com.ems.services.VoterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@Controller
@RequestMapping("/api/voters")
@RequiredArgsConstructor
public class VoterController {

    private final VoterService voterService;
    private final VoterRepository VR;

    @PostMapping("/register")
    public ResponseEntity<VoterRegisterDTO> register(@Valid @RequestBody VoterRegisterDTO voterRegisterDTO) throws PartyNotFoundException, IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(voterService.register(voterRegisterDTO));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<VoterRegisterDTO>> searchVoters(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth,
            @RequestParam(required = false) String dmvNumber,
            @RequestParam(required = false) String ssnNumber,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String[] sort) {

        Page<VoterRegisterDTO> result = voterService.searchVoters(
                firstName, lastName, dateOfBirth, dmvNumber, ssnNumber, page, size, sort);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{voterId}")
    public ResponseEntity<VoterUpdateDTO> updateVoter(
            @PathVariable String voterId,
            @Valid @RequestBody VoterUpdateDTO voterUpdateDTO,
            @RequestHeader(value = "modifiedBy", required = false, defaultValue = "system") String modifiedBy) {

        return ResponseEntity.ok(voterService.updateVoter(voterId, voterUpdateDTO, modifiedBy));
    }

    @GetMapping("/getAll")
    public List<Voter> getAll()
    {
        return VR.findAll();
    }
    
}
