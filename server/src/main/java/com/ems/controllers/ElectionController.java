package com.ems.controllers;

import com.ems.dtos.ElectionDTO;
import com.ems.entities.Election;
import com.ems.services.ElectionService;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
@RequestMapping("/api/elections")
public class ElectionController {

    private final ElectionService electionService;

    @PostMapping("/addElection")
    Election createElection(@RequestBody ElectionDTO electionDTO){
        return electionService.saveElection(electionDTO);
    }


}
