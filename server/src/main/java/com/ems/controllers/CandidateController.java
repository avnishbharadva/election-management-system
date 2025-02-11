package com.ems.controllers;

import com.ems.dtos.CandidateByPartyDTO;
import com.ems.dtos.CandidateDTO;
import com.ems.entities.Candidate;
import com.ems.exceptions.CandidateNotFoundException;
import com.ems.exceptions.CustomValidationException;
import com.ems.services.CandidateService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/candidate")
public class CandidateController {

    private final CandidateService candidateService;

    @GetMapping
    ResponseEntity<List<CandidateDTO>> getAllCandidates()
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

    @PostMapping("/addCandidate")
    ResponseEntity<Candidate> createCandidate(@Valid @RequestBody CandidateDTO candidateDTO)
    {
        try{
            var candidate=candidateService.saveCandidate(candidateDTO);
            return ResponseEntity.ok(candidate);
        }catch (CustomValidationException e){
            throw new CustomValidationException("Field provided are not valid");
        }
    }

    @GetMapping("/candidateId/{candidateId}")
    CandidateDTO getCandidateById(@PathVariable Long candidateId){
        return candidateService.findById(candidateId);
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

}
