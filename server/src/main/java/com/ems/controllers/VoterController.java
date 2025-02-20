package com.ems.controllers;

import com.ems.dtos.VoterRegisterDTO;
import com.ems.dtos.VoterDTO;
import com.ems.dtos.VoterResponseDTO;
import com.ems.dtos.VoterStatusDTO;
import com.ems.exceptions.DataNotFoundException;
import com.ems.projections.VoterView;
import com.ems.services.VoterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class VoterController {
    private final VoterService voterService;

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<VoterDTO> register(
            @Valid @RequestPart(value = "voter") VoterRegisterDTO voterRegisterDTO,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestPart(value = "signature", required = false) MultipartFile signature
    ) throws DataNotFoundException, IOException {
        log.info("voter register details : {}", voterRegisterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(voterService.register(voterRegisterDTO, image, signature));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<VoterDTO>> searchVoters(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth,
            @RequestParam(required = false) String dmvNumber,
            @RequestParam(required = false) String ssnNumber,
            @RequestParam int page, @RequestParam int size, @RequestParam(required = false) String[] sort) {
        Page<VoterDTO> result = voterService.searchVoters(firstName, lastName, dateOfBirth, dmvNumber, ssnNumber, page, size, sort);
        return ResponseEntity.ok(result);
    }

    @PatchMapping(value = "/{voterId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<VoterDTO> updateVoterImage(
            @PathVariable String voterId,
            @RequestPart(value = "voter", required = false) VoterDTO voterDTO,
            @RequestPart(value = "image", required = false) MultipartFile profileImg,
            @RequestPart(value = "signature", required = false) MultipartFile signImg) throws IOException {
        return ResponseEntity.ok(voterService.updateVoter(voterId, voterDTO, profileImg, signImg));
    }


    @GetMapping("/status")
    public ResponseEntity<List<VoterStatusDTO>> getAllStatus(){
        return ResponseEntity.ok(voterService.getAllStatus());
    }

    @GetMapping("/{ssnNumber}")
    public ResponseEntity<VoterResponseDTO> findBySsnNumber(@PathVariable String ssnNumber){
        return ResponseEntity.ok(voterService.findBySsnNumber(ssnNumber));
    }
}
