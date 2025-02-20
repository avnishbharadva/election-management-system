package com.ems.controllers;

import com.ems.dtos.PartyDTO;
import com.ems.services.PartyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/party")
public class PartyController {
    private final PartyService partyService;

    @PostMapping
    public ResponseEntity<PartyDTO> createParty(@Valid @RequestPart(value = "party") PartyDTO partyDTO,
                                                @RequestPart(value = "image", required = false)MultipartFile image) throws IOException
    {
        return ResponseEntity.ok(partyService.saveParty(partyDTO, image));
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
