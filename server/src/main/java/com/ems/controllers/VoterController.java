package com.ems.controllers;

import com.ems.dtos.VoterRegisterDTO;
import com.ems.dtos.VoterSearchDTO;
import com.ems.services.VoterServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/voters")
@RequiredArgsConstructor
public class VoterController {

    private final VoterServiceImpl voterServiceimpl;

    @PostMapping
    public ResponseEntity<VoterRegisterDTO> register(@RequestBody VoterRegisterDTO voterRegisterDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(voterServiceimpl.register(voterRegisterDTO));
    }


    @PostMapping("/search")
    public ResponseEntity<List<VoterRegisterDTO>> searchVoters(@RequestBody VoterSearchDTO searchDTO) {
        return ResponseEntity.ok(voterServiceimpl.searchVoters(searchDTO));
    }
}
