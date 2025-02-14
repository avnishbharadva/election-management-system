package com.ems.controllers;

import com.ems.dtos.CandidateByPartyDTO;
import com.ems.dtos.CandidateDTO;
import com.ems.dtos.CandidatePageResponse;
import com.ems.dtos.ErrorResponse;
import com.ems.dtos.*;
import com.ems.entities.Candidate;
import com.ems.exceptions.CandidateNotFoundException;
import com.ems.services.CandidateService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/candidate")
public class CandidateController {

    private final CandidateService candidateService;

    @GetMapping("/getAll")
    ResponseEntity<List<CandidateDTO>> getAllCandidate()
    {
        try {
            var candidateDTO=candidateService.findAll();
            return ResponseEntity.ok(candidateDTO);
        }
        catch (CandidateNotFoundException ex){
            throw new CandidateNotFoundException("Not found");
        }

    }

    @GetMapping("/ssn/{candidateSSN}")
    ResponseEntity<CandidateDTO> getCandidateBySSN(@Valid @PathVariable String candidateSSN)
    {
        try{
            var candidateDTO= candidateService.findByCandidateSSN(candidateSSN);
            return ResponseEntity.ok(candidateDTO);
        }
        catch (CandidateNotFoundException e){
            throw new CandidateNotFoundException("No candidate found");
        }
    }

    @PostMapping(value = "/addCandidate",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Candidate> createCandidate(
            @RequestPart("candidate") CandidateDTO candidateData,
            @RequestPart(value = "candidateImage", required = false) MultipartFile candidateImage,
            @RequestPart(value = "candidateSignature", required = false) MultipartFile candidateSignature) throws IOException {
        try {
            Candidate savedCandidate = candidateService.saveCandidate(candidateData, candidateImage, candidateSignature);
            return ResponseEntity.ok(savedCandidate);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/candidateId/{candidateId}")
    ResponseEntity<Map<String,Object>> getCandidateById(@PathVariable Long candidateId){
        return ResponseEntity.ok().body( candidateService.findById(candidateId));
    }

    @PutMapping("/updateCandidate/{candidateId}")
    Candidate updateCandidate(@PathVariable Long candidateId,@Valid @RequestBody CandidateDTO candidateDTO){
       return candidateService.update(candidateId,candidateDTO);
    }

    @GetMapping("/partyName/{candidatePartyName}")
    List<CandidateByPartyDTO> getCandidateByPartyName(@PathVariable String candidatePartyName)
    {
        return candidateService.findByPartyName(candidatePartyName);
    }
    @GetMapping
    public ResponseEntity<CandidatePageResponse> getCandidates(
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "perPage",defaultValue = "10") int perPage,
            @RequestParam(value = "sortBy",defaultValue = "candidateId") String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc") String sortDir
    ) {
        if (page < 0 || perPage <= 0) {
            throw new IllegalArgumentException("Page and perPage must be positive.");
        }

        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Page<CandidateDTO> candidatePage = candidateService.getPagedCandidate(page, perPage, sort);

        if (candidatePage.isEmpty()) {
            throw new CandidateNotFoundException("No candidates found.");
        }

        return ResponseEntity.ok(new CandidatePageResponse(
                candidatePage.getContent(),
                candidatePage.getNumber(),
                candidatePage.getTotalPages(),
                candidatePage.getTotalElements(),
                candidatePage.getSize()
        ));
    }
    @GetMapping("election/{electionId}")
    public ResponseEntity<CandidatePageResponse> getCandidateByElection(@PathVariable Long electionId,
                                                     @RequestParam(value = "page", defaultValue = "0") int page,
                                                     @RequestParam(value = "perPage", defaultValue = "10") int perPage){
        if (page < 0 || perPage <= 0) {
            throw new IllegalArgumentException("Page and perPage must be non-negative and greater than zero.");
        }

        CandidatePageResponse candidates = candidateService.getCandidateByElectionId(electionId, page, perPage);
        return ResponseEntity.ok(candidates);
    }

    @DeleteMapping("/delete/{candidateId}")
    ResponseEntity<?> deleteById(@PathVariable Long candidateId)
    {
        if (candidateService.findById(candidateId)!=null) {
            return ResponseEntity.ok("Candidate with id:"+candidateId);

        } else {
            throw new CandidateNotFoundException("No candidate with id:"+candidateId+" is found");
        }

    }
}
