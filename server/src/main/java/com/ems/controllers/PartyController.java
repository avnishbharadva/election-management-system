package com.ems.controllers;

import com.ems.dtos.PartyDTO;
import com.ems.services.PartyService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/party")
public class PartyController {
    private final PartyService partyService;

    @PostMapping
    public ResponseEntity<PartyDTO> createParty(@Valid @RequestBody PartyDTO partyDTO)
    {
        return ResponseEntity.ok(partyService.saveParty(partyDTO));
    }

    @GetMapping("/{partyId}")
    public ResponseEntity<PartyDTO> findByPartyId(@PathVariable long partyId){
        return ResponseEntity.ok(partyService.partyById(partyId));
    }

    @GetMapping
    public ResponseEntity<List<PartyDTO>> findAllParties(){
        return ResponseEntity.ok(partyService.findAll());
    }
}
