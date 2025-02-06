package com.ems.controllers;

import com.ems.dtos.VoterRegisterDTO;
import com.ems.exceptions.PartyNotFoundException;
import com.ems.services.VoterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/voters")
@RequiredArgsConstructor
public class VoterController {

    private final VoterService voterService;

    @PostMapping
    public ResponseEntity<VoterRegisterDTO> register(@RequestBody VoterRegisterDTO voterRegisterDTO) throws PartyNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(voterService.register(voterRegisterDTO));
    }

    @GetMapping("/ssn/{ssnNumber}")
    public ResponseEntity<VoterRegisterDTO> findBySSN(@PathVariable String ssnNumber){
        return ResponseEntity.ok(voterService.findBySSN(ssnNumber));
    }

    @GetMapping("/dmv/{dmvNumber}")
    public ResponseEntity<VoterRegisterDTO> findByDMV(@PathVariable String dmvNumber){
        return ResponseEntity.ok(voterService.findByDMV(dmvNumber));
    }

    @GetMapping("/firstname/{name}")
    public ResponseEntity<VoterRegisterDTO> findByFirstName(@PathVariable String name){
        return ResponseEntity.ok(voterService.findByFirstName(name));
    }
}
