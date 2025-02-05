package com.ems.controllers;

import com.ems.dtos.CandidateDTO;
import com.ems.entities.Candidate;
import com.ems.services.CandidateService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/candidate")
public class CandidateController {

    private final CandidateService candidateService;

    @GetMapping("/{candidateSSN}")
    CandidateDTO getCandidateBySSN(@PathVariable String candidateSSN)
    {
        return candidateService.findByCandidateSSN(candidateSSN);
    }
}
