package com.ems.controllers;

import com.ems.dtos.VoterRegisterDTO;
import com.ems.dtos.VoterSearchDTO;
import com.ems.entities.Voter;
import com.ems.exceptions.PartyNotFoundException;
import com.ems.services.VoterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/voters")
@RequiredArgsConstructor
public class VoterController {

    private final VoterService voterService;

    @PostMapping
    public ResponseEntity<VoterRegisterDTO> register(@Valid @RequestBody VoterRegisterDTO voterRegisterDTO) throws PartyNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(voterService.register(voterRegisterDTO));
    }

    @GetMapping("/search")
    public ResponseEntity<List<VoterRegisterDTO>> searchVoters(@RequestBody VoterSearchDTO searchDTO) {
        return ResponseEntity.ok(voterService.searchVoters(searchDTO));
    }


}
