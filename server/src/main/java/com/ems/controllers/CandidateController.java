package com.ems.controllers;

import com.ems.dtos.CandidateDTO;
import com.ems.entities.Candidate;
import com.ems.exceptions.CandidateNotFoundException;
import com.ems.services.CandidateService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/candidate")
public class CandidateController {

    private final CandidateService candidateService;

    @GetMapping("/ssn/{candidateSSN}")
    ResponseEntity<?> getCandidateBySSN(@Valid @PathVariable String candidateSSN)
    {
        try{
            CandidateDTO candidateDTO= candidateService.findByCandidateSSN(candidateSSN);
            return ResponseEntity.ok(candidateDTO);
        }
        catch (CandidateNotFoundException e){
            throw new CandidateNotFoundException("No candidate found");
        }
    }

    @PostMapping("/addCandidate")
    Candidate createCandidate(@Valid @RequestBody CandidateDTO candidateDTO)
    {
        return candidateService.saveCandidate(candidateDTO);
    }

    @GetMapping("/candidateId/{candidateId}")
    CandidateDTO getCandidateById(@PathVariable Long candidateId){
        return candidateService.findById(candidateId);
    }

    @PutMapping("/updateCandidate/{candidateId}")
    Candidate updateCandidate(@PathVariable Long candidateId,@Valid @RequestBody CandidateDTO candidateDTO){
       return candidateService.update(candidateId,candidateDTO);
    }

}
