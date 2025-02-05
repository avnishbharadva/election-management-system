package com.ems.controllers;

import com.ems.dtos.CandidateDTO;
import com.ems.entities.Candidate;
import com.ems.entities.CandidateAddress;
import com.ems.entities.CandidateName;
import com.ems.services.CandidateService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/candidate")
public class CandidateController {

    private final CandidateService candidateService;

    @GetMapping("/{candidateSSN}")
    CandidateDTO getCandidateBySSN(@PathVariable String candidateSSN) {
        return candidateService.findByCandidateSSN(candidateSSN);
    }

    @PostMapping("/addCandidate")
    Candidate createCandidate(@RequestBody CandidateDTO candidateDTO)
    {
        return candidateService.save(candidateDTO);
    }

}
