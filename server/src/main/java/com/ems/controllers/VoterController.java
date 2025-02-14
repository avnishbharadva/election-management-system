package com.ems.controllers;

import com.ems.dtos.VoterRegisterDTO;
import com.ems.dtos.VoterResponseDTO;
import com.ems.dtos.VoterSearchDTO;
import com.ems.dtos.VoterUpdateDTO;
import com.ems.exceptions.PartyNotFoundException;
import com.ems.services.VoterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/voters")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class VoterController {

    private final VoterService voterService;

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<VoterResponseDTO> register(
            @Valid @RequestPart(value = "voter") VoterRegisterDTO voterRegisterDTO,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestPart(value = "signature", required = false) MultipartFile signature
    ) throws PartyNotFoundException, IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(voterService.register(voterRegisterDTO, image, signature));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<VoterResponseDTO>> searchVoters(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth,
            @RequestParam(required = false) String dmvNumber,
            @RequestParam(required = false) String ssnNumber,
            @RequestParam int page, @RequestParam int size, @RequestParam(required = false) String[] sort) {
        Page<VoterResponseDTO> result = voterService.searchVoters(firstName, lastName, dateOfBirth, dmvNumber, ssnNumber, page, size, sort);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{ssnId}")
    public ResponseEntity<VoterResponseDTO> updateVoter(@PathVariable String ssnId, @RequestBody VoterResponseDTO voterUpdateDTO) {
        return ResponseEntity.ok(voterService.updateVoter(ssnId, voterUpdateDTO));
    }
}
