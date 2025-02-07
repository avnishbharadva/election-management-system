package com.ems.controllers;

import com.ems.dtos.CandidateDTO;
import com.ems.entities.Candidate;
import com.ems.services.CandidateService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/candidate")
public class CandidateController {

    private final CandidateService candidateService;

    @GetMapping("/ssn/{candidateSSN}")
    CandidateDTO getCandidateBySSN(@Valid @PathVariable String candidateSSN) {
        return candidateService.findByCandidateSSN(candidateSSN);
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
