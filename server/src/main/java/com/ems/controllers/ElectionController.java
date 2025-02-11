package com.ems.controllers;

import com.ems.dtos.CandidateDTO;
import com.ems.dtos.ElectionDTO;
import com.ems.entities.Election;
import com.ems.services.ElectionService;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@RequestMapping("/api/elections")
public class ElectionController {

    private final ElectionService electionService;

    @PostMapping("/addElection")
    Election createElection(@RequestBody ElectionDTO electionDTO){
        return electionService.saveElection(electionDTO);
    }

    @PutMapping("/update/{electionId}")
    Election updateElection(@PathVariable Long electionId,@Valid  @RequestBody ElectionDTO electionDTO)
    {
        return electionService.updateElection(electionId,electionDTO);
    }

}
