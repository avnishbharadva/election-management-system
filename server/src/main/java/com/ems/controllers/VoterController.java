package com.ems.controllers;

import com.ems.dtos.VoterRegisterDTO;
import com.ems.dtos.VoterSearchDTO;
import com.ems.dtos.VoterUpdateDTO;
import com.ems.exceptions.PartyNotFoundException;
import com.ems.services.VoterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<Page<VoterRegisterDTO>> searchVoters(
            @RequestBody VoterSearchDTO searchDTO,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String[] sort) {

        Page<VoterRegisterDTO> result = voterService.searchVoters(searchDTO, page, size, sort);
        return ResponseEntity.ok(result);
    }




    @PutMapping("/{id}")
    public ResponseEntity<VoterRegisterDTO> updateVoter(@PathVariable String id, @Valid @RequestBody VoterUpdateDTO voterUpdateDTO){
        return ResponseEntity.ok(voterService.updateVoter(id,voterUpdateDTO));
    }
}
